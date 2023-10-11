package web;

import DTOs.DogDTO;
import DTOs.OrderDTO;
import DTOs.UserDTO;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import session.CartRemote;
import session.EDDogsHavenBLLFacadeRemote;

/**
 * The LoginBean class handles user login and logout functionality.
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    @EJB
    private EDDogsHavenBLLFacadeRemote bll;

    @EJB
    private CartRemote cart;

    private String userId;
    private String username;
    private String userPhone;
    private String userAddress;
    private String userEmail;
    private String userAppgroup;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
        userId = null;
        username = null;
        userPhone = null;
        userAddress = null;
        userEmail = null;
        userAppgroup = null;
        oldPassword = null;
        newPassword = null;
        confirmPassword = null;
    }

    /**
     * Clears the user details.
     */
    private void clearUserDetails() {
        userId = null;
        username = null;
        userPhone = null;
        userAddress = null;
        userEmail = null;
        userAppgroup = null;
        oldPassword = null;
        newPassword = null;
        confirmPassword = null;
    }

    /**
     * Logs out the user.
     *
     * @return The outcome navigation string to redirect to the main page.
     */
    public String logout() {
        clearUserDetails();
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        try {
            request.logout();
        } catch (ServletException ex) {
            ex.printStackTrace();
        }
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.invalidate();

        return "main";
    }

    /**
     * Checks if a user is logged in.
     *
     * @return true if a user is logged in, false otherwise.
     */
    public boolean isLoggedIn() {
        Object lEmail = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        if (lEmail == null) {
            return false;
        } else {
            UserDTO user = bll.getSessionUser(lEmail);
            if (user != null) {
                setUserInfo(user);
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Sets the user information from the UserDTO.
     *
     * @param user The UserDTO containing user information.
     */
    private void setUserInfo(UserDTO user) {
        userId = user.getUserid();
        username = user.getName();
        userPhone = user.getPhone();
        userAddress = user.getAddress();
        userEmail = user.getEmail();
        userAppgroup = user.getAppgroup();
    }

    public String updateUser() {
        try {
            if (username != null && userEmail != null && userPhone != null && userAddress != null) {
                UserDTO lUpdatedUser = new UserDTO(username, userEmail, userPhone, userAddress, userId);
                if (bll.updateAUser(lUpdatedUser)) {
                    // Log out the user
                    this.logout();
                    return "success";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    public String addCartItem(DogDTO aDog) {
        try {
            if (isAnAdmin()) {
                return "auth-error";
            }
            if (isACustomer()) {
                if (cart.addItem(aDog)) {
                    return "cart";
                } else {
                    return "addCartItem-failure";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "requireLogin";
    }

    public String removeCartItem(Integer aDogId) {
        try {
            if (cart.removeItem(aDogId)) {
                return "cart";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    public String addAnOrder() {
        try {
            if (isACustomer()) {
                if (bll.submitAnOrder(cart.getCart(), userId)) {
                    cart.emptyCart();
                    return "checkout-success";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    public String removeAnOrder(Object aOrderId) {
        try {
            if (bll.deleteAnOrder(aOrderId)) {
                return "removeOrder-success";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "removeOrder-failure";
    }

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        if (!cart.isEmpty()) {
            for (DogDTO item : cart.getCart()) {
                totalPrice = totalPrice.add(item.getPrice());
            }
        }
        return totalPrice;
    }

    public String updatePassword() {
        try {
            if (bll.updateUserPassword(userId, newPassword)) {
                this.logout();
                return "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "failure";
    }

    public void validateOldPassword(FacesContext context,
            UIComponent componentToValidate, Object value)
            throws ValidatorException {
        //get validate password
        oldPassword = (String) value;

        if (!bll.validatePassword(userId, oldPassword)) {
            FacesMessage message = new FacesMessage("Authentication failed! Please enter the right password");
            throw new ValidatorException(message);
        }
    }

    public void validateNewPassword(FacesContext context,
            UIComponent componentToValidate, Object value)
            throws ValidatorException {
        // get new password
        newPassword = (String) value;

        if (!bll.validateNewPassword(userId, newPassword)) {
            FacesMessage message = new FacesMessage(
                    "Old Password and New Password are the same! They must be different.");
            throw new ValidatorException(message);
        }
    }

    public void validateNewPasswordPair(FacesContext context,
            UIComponent componentToValidate, Object newValue)
            throws ValidatorException {
        // get confirm password
        confirmPassword = (String) newValue;

        if (!confirmPassword.equals(newPassword)) {
            FacesMessage message = new FacesMessage(
                    "New Password and Confirm New Password are not the same! They must be the same.");
            throw new ValidatorException(message);
        }
    }

    public boolean isACustomer() {
        return ("ED-Customer".equals(this.userAppgroup));
    }

    public boolean isAnAdmin() {
        return ("ED-Administrator".equals(this.userAppgroup));
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Collection<DogDTO> getCartItems() {
        return cart.getCart();
    }

    public Collection<OrderDTO> getOrdersFromUser() {
        try {
            if (userId != null) {
                Collection<OrderDTO> orders = bll.getAllOrdersFromAUser(userId);
                return orders;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
