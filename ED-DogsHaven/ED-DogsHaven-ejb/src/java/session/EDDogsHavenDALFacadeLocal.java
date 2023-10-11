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
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author giabaobui
 */
@Local
public interface EDDogsHavenDALFacadeLocal {

    void createADog(DogDAO aDog);

    void updateADog(DogDAO aDog);

    void deleteADog(DogDAO aDog);

    DogDAO findADog(Object aDogId);

    DogDTO dogDAOtoDogDTO(DogDAO aDog);

    DogDAO dogDTOToDogDAO(DogDTO aDogDTO);

    Collection<DogDTO> dogDAOCollectionToDogDTOCollection(Collection<DogDAO> aDogDAOCollection);
    
    //for the order

    Collection<DogDAO> dogDTOCollectionToDogDAOCollection(Collection<DogDTO> aDogDTOCollection);

    void createAUser(UserDAO aUser);

    void updateAUser(UserDAO aUser);

    void removeAUser(UserDAO aUser);

    UserDAO findAUser(Object aUserId);

    UserDTO userDAOtoUserDTO(UserDAO aUser);

    UserDAO userDTOTouserDAO(UserDTO aUserDTO, Object aPassword);
    
    //to list all the users

    Collection<UserDTO> userDAOCollectionToUserDTOCollection(Collection<UserDAO> aUserDAOCollection);

    void createAnOrder(OrderDAO aOrder);

    void updateAnOrder(OrderDAO aOrder);

    void removeAnOrder(OrderDAO aOrder);

    OrderDAO findAnOrder(Object aOrderId);

    OrderDTO orderDAOtoOrderDTO(OrderDAO orderDAO);

    OrderDAO orderDTOToOrderDAO(OrderDTO aOrderDTO);
    
    //to list all the orders
    
    Collection<OrderDTO> orderDAOCollectionToOrderDTOCollection(Collection<OrderDAO> orderDAOCollection);
    
    Collection<OrderDTO> getAllOrders();

    Collection<DogDTO> getAllDogs();

    Collection<UserDTO> getAllUsers();

    Collection<OrderDTO> getAllOrdersFromAUser(String aUserId);

    UserDTO getSessionUser(Object aEmail);

}
