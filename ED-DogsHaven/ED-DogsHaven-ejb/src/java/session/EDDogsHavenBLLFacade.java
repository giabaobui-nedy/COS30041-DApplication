/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import DTOs.DogDTO;
import DTOs.OrderDTO;
import DTOs.UserDTO;
import entity.DogDAO;
import entity.OrderDAO;
import entity.UserDAO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author giabaobui
 */
@DeclareRoles({"ED-Customer", "ED-Administrator"})
@Stateless
public class EDDogsHavenBLLFacade implements EDDogsHavenBLLFacadeRemote {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    private EDDogsHavenDALFacadeLocal dal;

    private String generateRandomString() {
        int length = 10;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    /**
     *
     * @return
     */
    //used
    @RolesAllowed({"ED-Administrator"})
    @Override
    public Collection<DogDTO> getAllDogs() {
        try {
            return dal.getAllDogs();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @return
     */
    //used
    @Override
    public Collection<DogDTO> getAllAvailableDogs() {
        try {
            Collection<DogDTO> allDogs = dal.getAllDogs();
            Collection<DogDTO> availableDogs = new ArrayList<>();
            for (DogDTO dog : allDogs) {
                if (dog.getIsAvailable()) {
                    availableDogs.add(dog);
                }
            }
            return availableDogs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param aDogId
     * @return
     */
    @RolesAllowed({"ED-Administrator"})
    @Override
    public DogDTO getADog(Object aDogId) {
        try {
            DogDAO lDog = dal.findADog(aDogId);
            if (lDog != null) {
                return dal.dogDAOtoDogDTO(lDog);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param aDog
     * @return
     */
    @RolesAllowed({"ED-Administrator"})
    @Override
    public boolean addADog(DogDTO aDog) {
        try {
            DogDAO lDog = dal.dogDTOToDogDAO(aDog);
            lDog.setDogid(null);
            dal.createADog(lDog);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @param aDog
     * @return
     */
    @RolesAllowed({"ED-Administrator"})
    @Override
    public boolean updateADog(DogDTO aDog) {
        try {
            if (isADog(aDog.getDogId())) {
                DogDAO lDog = dal.dogDTOToDogDAO(aDog);
                dal.updateADog(lDog);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @param dogId
     * @return
     */
    @RolesAllowed({"ED-Administrator"})
    @Override
    public boolean setDogAsSold(Object dogId) {
        try {
            DogDAO lDog = dal.findADog(dogId);
            if (lDog != null && !lDog.getIssold()) {
                lDog.setIssold(Boolean.TRUE);
                dal.updateADog(lDog);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @param aDogId
     * @return
     */
    @Override
    public Boolean isADog(Object aDogId) {
        return dal.findADog(aDogId) != null;
    }

    //use the existing local facade method
    /**
     *
     * @return
     */
    @RolesAllowed({"ED-Administrator"})
    @Override
    public Collection<OrderDTO> getAllOrders() {
        try {
            return dal.getAllOrders();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param aOrderId
     * @return
     */
    @Override
    public OrderDTO getAnOrder(Object aOrderId) {
        try {
            OrderDAO lOrder = dal.findAnOrder(aOrderId);
            if (lOrder != null) {
                return dal.orderDAOtoOrderDTO(lOrder);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    //auto-increment primary key
    /**
     *
     * @param aItems
     * @param aUserId
     * @return
     */
    @RolesAllowed({"ED-Customer"})
    @Override
    public boolean submitAnOrder(Collection<DogDTO> aItems, Object aUserId) {
        try {
            //if the order has items
            if (!aItems.isEmpty() && aUserId != null) {
                //create a new order
                OrderDAO lNewOrder = new OrderDAO();
                //set the buyer to the current user (assuming the user has logged in the system)
                UserDAO lAUser = dal.findAUser(aUserId);
                lNewOrder.setCustomerid(lAUser);
                //set the collections of DogDTO -> DogDAO
                Collection<DogDAO> lItems = new ArrayList<>();
                for (DogDTO item : aItems) {
                    //get the dogs from the list
                    DogDAO lDog = dal.dogDTOToDogDAO(item);
                    //set the dogs to be temporarily unavailable (waiting to be actually adopted)
                    lDog.setIsavailable(Boolean.FALSE);
                    //update it to the database
                    dal.updateADog(lDog);
                    //add the dog to the order
                    lItems.add(lDog);
                }
                //set the items for the order
                lNewOrder.setDogDAOCollection(lItems);
                //persist the order into the database
                dal.createAnOrder(lNewOrder);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //delete an order
    /**
     *
     * @param aOrderId
     * @return
     */
    @RolesAllowed({"ED-Customer"})
    @Override
    public boolean deleteAnOrder(Object aOrderId) {
        try {
            OrderDAO lOrder = dal.findAnOrder(aOrderId);
            if (lOrder != null) {
                //get all the dogs from order
                Collection<DogDAO> lDogs = lOrder.getDogDAOCollection();
                for (DogDAO lDog : lDogs) {
                    //set it back to available
                    lDog.setIsavailable(Boolean.TRUE);
                    //update it to the database
                    dal.updateADog(lDog);
                }
                //after finishing it, remove the order
                dal.removeAnOrder(lOrder);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @param aOrderId
     * @return
     */
    @Override
    public boolean isAnOrder(Object aOrderId) {
        return getAnOrder(aOrderId) != null;
    }

    /**
     *
     * @return
     */
    @Override
    public Collection<UserDTO> getAllUsers() {
        try {
            return dal.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @return
     */
    @RolesAllowed({"ED-Administrator"})
    @Override
    public Collection<UserDTO> getAllCustomers() {
        try {
            Collection<UserDTO> customers = dal.getAllUsers();
            Collection<UserDTO> filteredCustomers = new ArrayList<>();

            for (UserDTO customer : customers) {
                if (customer.getAppgroup().equals("ED-Customer")) {
                    filteredCustomers.add(customer);
                }
            }
            return filteredCustomers;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param aUserDTO
     * @param aPassword
     * @return
     */
    @Override
    public boolean registerANewUser(UserDTO aUserDTO, Object aPassword) {
        try {
            UserDAO lUserDAO = dal.userDTOTouserDAO(aUserDTO, aPassword);
            String lNewUserId = generateRandomString();
            while (isAUser(lNewUserId)) {
                lNewUserId = generateRandomString();
            }
            lUserDAO.setUserid(lNewUserId);
            dal.createAUser(lUserDAO);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @param aUserId
     * @return
     */
    @RolesAllowed({"ED-Administrator"})
    @Override
    public UserDTO getAUser(Object aUserId) {
        try {
            UserDAO userDAO = dal.findAUser(aUserId);
            if (userDAO != null) {
                return dal.userDAOtoUserDTO(userDAO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param aUserDTO
     * @return
     */
    @RolesAllowed({"ED-Administrator", "ED-Customer"})
    @Override
    public boolean updateAUser(UserDTO aUserDTO) {
        try {
            UserDAO lAUserDAO = dal.findAUser(aUserDTO.getUserid());
            if (lAUserDAO != null) {
                // Update the user information
                lAUserDAO.setName(aUserDTO.getName());
                lAUserDAO.setEmail(aUserDTO.getEmail());
                lAUserDAO.setPhone(aUserDTO.getPhone());
                lAUserDAO.setAddress(aUserDTO.getAddress());
                // Perform the update operation
                dal.updateAUser(lAUserDAO);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean validatePassword(String aUserId, String aPassword) {
        try {
            UserDAO lUserDAO = dal.findAUser(aUserId);
            if (lUserDAO != null) {
                return (lUserDAO.getPassword().equals(aPassword));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean validateNewPassword(String aUserId, String aNewPassword) {
        try {
            UserDAO lUserDAO = dal.findAUser(aUserId);
            if (lUserDAO != null) {
                return (!lUserDAO.getPassword().equals(aNewPassword));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @param aUserId
     * @param aNewPassword
     * @return
     */
    @Override
    public boolean updateUserPassword(String aUserId, String aNewPassword) {
        try {
            UserDAO userDAO = dal.findAUser(aUserId);
            if (userDAO != null) {
                // Update the user password
                userDAO.setPassword(aNewPassword);
                // Perform the update operation
                dal.updateAUser(userDAO);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @param aUserId
     * @return
     */
    @RolesAllowed({"ED-Administrator"})
    @Override
    public boolean setAUserInactive(String aUserId) {
        try {
            UserDAO userDAO = dal.findAUser(aUserId);
            if (userDAO != null) {
                // Set the user as inactive
                userDAO.setActive(false);

                // Perform the update operation
                dal.updateAUser(userDAO);

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isAUser(String aUserId) {
        return (dal.findAUser(aUserId) != null);
    }

    @Override
    public Collection<OrderDTO> getAllOrdersFromAUser(String aUserId) {
        try {
            return dal.getAllOrdersFromAUser(aUserId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserDTO getSessionUser(Object aEmail) {
        try {
            return dal.getSessionUser(aEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
