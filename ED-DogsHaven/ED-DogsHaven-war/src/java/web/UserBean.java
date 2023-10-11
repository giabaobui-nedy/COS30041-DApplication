/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import DTOs.UserDTO;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import session.EDDogsHavenBLLFacadeRemote;

/**
 *
 * @author giabaobui
 */
@Named(value = "userBean")
@RequestScoped
public class UserBean implements Serializable {

    @EJB
    private EDDogsHavenBLLFacadeRemote bll;

    private String userId;
    private String name;
    private String phone;
    private String address;
    private String email;
    private String password;
    private String appgroup;
    private Boolean active;

    /**
     * Creates a new instance of UserManagement
     */
    public UserBean() {
        Map<String, String> requestParams = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (requestParams.containsKey("userId")) {
            initializeUser(requestParams);
        } else {
            userId = null;
            name = null;
            email = null;
            phone = null;
            address = null;
            password = null;
            appgroup = null;
            active = true;
        }
    }

    private void initializeUser(Map<String, String> requestParams) {
        userId = requestParams.get("userId");
        name = requestParams.get("name");
        email = requestParams.get("email");
        phone = requestParams.get("phone");
        address = requestParams.get("address");
        appgroup = requestParams.get("appgroup");
        active = Boolean.parseBoolean(requestParams.get("active"));
    }
    
        public String registerANewUser() {
        try {
            if (name != null & email != null && phone != null && address != null && password != null) {
                UserDTO lNewUser = new UserDTO(name, email, phone, address);
                if (bll.registerANewUser(lNewUser, password)) {
                    return "success";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "failure";
    }

    public String updateUser() {
        try {
            if (name != null && email != null && phone != null && address != null) {
                UserDTO lUpdatedUser = new UserDTO(name, email, phone, address, userId);
                if (bll.updateAUser(lUpdatedUser)) {
                    return "success";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "failure";
    }

    public Collection<UserDTO> getAllCustomers() {
        try {
            return bll.getAllCustomers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAppgroup() {
        return appgroup;
    }

    public void setAppgroup(String appgroup) {
        this.appgroup = appgroup;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}
