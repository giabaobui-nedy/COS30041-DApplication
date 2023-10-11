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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author giabaobui
 */
@Stateless
public class EDDogsHavenDALFacade implements EDDogsHavenDALFacadeLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "ED-DogsHaven-ejbPU")
    private EntityManager em;

    //Dog specifications:
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void createADog(DogDAO aDog) {
        em.persist(aDog);
    }

    @Override
    public void updateADog(DogDAO aDog) {
        em.merge(aDog);
    }

    @Override
    public void deleteADog(DogDAO aDog) {
        em.remove(em.merge(aDog));
    }

    @Override
    public DogDAO findADog(Object aDogId) {
        return em.find(DogDAO.class, aDogId);
    }

    @Override
    public DogDTO dogDAOtoDogDTO(DogDAO aDog) {
        return new DogDTO(aDog.getName(), aDog.getBreed(),
                aDog.getCharacteristic(), aDog.getDescription(),
                aDog.getDogid(), aDog.getAge(), aDog.getPrice(),
                aDog.getIsavailable(), aDog.getIssold());
    }

    @Override
    public DogDAO dogDTOToDogDAO(DogDTO aDogDTO) {
        DogDAO dogDAO = new DogDAO();
        dogDAO.setName(aDogDTO.getName());
        dogDAO.setBreed(aDogDTO.getBreed());
        dogDAO.setCharacteristic(aDogDTO.getCharacteristic());
        dogDAO.setDescription(aDogDTO.getDescription());
        dogDAO.setDogid(aDogDTO.getDogId());
        dogDAO.setAge(aDogDTO.getAge());
        dogDAO.setPrice(aDogDTO.getPrice());
        dogDAO.setIsavailable(aDogDTO.getIsAvailable());
        dogDAO.setIssold(aDogDTO.getIsSold());
        return dogDAO;
    }

    @Override
    public Collection<DogDTO> dogDAOCollectionToDogDTOCollection(Collection<DogDAO> aDogDAOCollection) {
        Collection<DogDTO> lACollection = new ArrayList<>();
        for (DogDAO aDogDAO : aDogDAOCollection) {
            lACollection.add(dogDAOtoDogDTO(aDogDAO));
        }
        return lACollection;
    }

    @Override
    public Collection<DogDAO> dogDTOCollectionToDogDAOCollection(Collection<DogDTO> aDogDTOCollection) {
        if (aDogDTOCollection == null) {
            return null;
        }

        Collection<DogDAO> dogDAOCollection = new ArrayList<>();
        for (DogDTO dogDTO : aDogDTOCollection) {
            DogDAO dogDAO = new DogDAO();
            dogDAO.setName(dogDTO.getName());
            dogDAO.setBreed(dogDTO.getBreed());
            dogDAO.setCharacteristic(dogDTO.getCharacteristic());
            dogDAO.setDescription(dogDTO.getDescription());
            dogDAO.setDogid(dogDTO.getDogId());
            dogDAO.setAge(dogDTO.getAge());
            dogDAO.setPrice(dogDTO.getPrice());
            dogDAO.setIsavailable(dogDTO.getIsAvailable());
            dogDAO.setIssold(dogDTO.getIsSold());

            dogDAOCollection.add(dogDAO);
        }

        return dogDAOCollection;
    }

    @Override
    public void createAUser(UserDAO aUser) {
        em.persist(aUser);
    }

    @Override
    public void updateAUser(UserDAO aUser) {
        em.merge(aUser);
    }

    @Override
    public void removeAUser(UserDAO aUser) {
        em.remove(em.merge(aUser));
    }

    @Override
    public UserDAO findAUser(Object aUserId) {
        return em.find(UserDAO.class, aUserId);
    }

    @Override
    public UserDTO userDAOtoUserDTO(UserDAO aUser) {
        return new UserDTO(
                aUser.getName(),
                aUser.getEmail(),
                aUser.getPhone(),
                aUser.getAddress(),
                aUser.getAppgroup(),
                aUser.getUserid(),
                aUser.getActive());
    }

    @Override
    public Collection<UserDTO> userDAOCollectionToUserDTOCollection(Collection<UserDAO> aUserDAOCollection) {
        Collection<UserDTO> lACollection = new ArrayList<>();
        for (UserDAO aUser : aUserDAOCollection) {
            lACollection.add(userDAOtoUserDTO(aUser));
        }
        return lACollection;
    }

    @Override
    public UserDAO userDTOTouserDAO(UserDTO aUserDTO, Object aPassword) {
        UserDAO userDAO = new UserDAO();
        userDAO.setName(aUserDTO.getName());
        userDAO.setPassword(aPassword.toString()); // Convert the password object to a string
        userDAO.setEmail(aUserDTO.getEmail());
        userDAO.setPhone(aUserDTO.getPhone());
        userDAO.setAddress(aUserDTO.getAddress());
        userDAO.setAppgroup(aUserDTO.getAppgroup());
        userDAO.setUserid(aUserDTO.getUserid());
        userDAO.setActive(aUserDTO.getActive());
        return userDAO;
    }

    @Override
    public void createAnOrder(OrderDAO aOrder) {
        em.persist(aOrder);
    }

    @Override
    public void updateAnOrder(OrderDAO aOrder) {
        em.merge(aOrder);
    }

    @Override
    public void removeAnOrder(OrderDAO aOrder) {
        em.remove(em.merge(aOrder));
    }

    @Override
    public OrderDAO findAnOrder(Object aOrderId) {
        return em.find(OrderDAO.class, aOrderId);
    }

    @Override
    public OrderDTO orderDAOtoOrderDTO(OrderDAO aOrderDAO) {
        return new OrderDTO(
                aOrderDAO.getOrderid(),
                this.userDAOtoUserDTO(aOrderDAO.getCustomerid()),
                this.dogDAOCollectionToDogDTOCollection(aOrderDAO.getDogDAOCollection()));
    }

    @Override
    public OrderDAO orderDTOToOrderDAO(OrderDTO aOrderDTO) {
        //initialize a new user DAO
        OrderDAO orderDAO = new OrderDAO();
        //bind the user with their order
        Object lAUserId = aOrderDTO.getCustomerid().getUserid();
        orderDAO.setCustomerid(findAUser(lAUserId));
        //set the items to the order
        orderDAO.setDogDAOCollection(dogDTOCollectionToDogDAOCollection(aOrderDTO.getDogDTOCollection()));

        return orderDAO;
    }

    @Override
    public Collection<OrderDTO> orderDAOCollectionToOrderDTOCollection(Collection<OrderDAO> orderDAOCollection) {
        Collection<OrderDTO> orderDTOCollection = new ArrayList<>();
        for (OrderDAO orderDAO : orderDAOCollection) {
            OrderDTO orderDTO = new OrderDTO(
                    orderDAO.getOrderid(),
                    userDAOtoUserDTO(orderDAO.getCustomerid()),
                    dogDAOCollectionToDogDTOCollection(orderDAO.getDogDAOCollection())
            );
            orderDTOCollection.add(orderDTO);
        }
        return orderDTOCollection;
    }

    private Collection<OrderDTO> orderDAOCollectionToOrderDTOCollectionV2(Collection<OrderDAO> orderDAOCollection) {
        Collection<OrderDTO> orderDTOCollection = new ArrayList<>();
        for (OrderDAO orderDAO : orderDAOCollection) {
            OrderDTO orderDTO = new OrderDTO(
                    orderDAO.getOrderid(),
                    dogDAOCollectionToDogDTOCollection(orderDAO.getDogDAOCollection())
            );
            orderDTOCollection.add(orderDTO);
        }
        return orderDTOCollection;
    }

    @Override
    public Collection<OrderDTO> getAllOrders() {
        try {
            Query q = em.createNamedQuery("OrderDAO.findAll");
            Collection<OrderDAO> result = q.getResultList();
            return orderDAOCollectionToOrderDTOCollection(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<DogDTO> getAllDogs() {
        try {
            Query q = em.createNamedQuery("DogDAO.findAll");
            Collection<DogDAO> result = q.getResultList();
            return dogDAOCollectionToDogDTOCollection(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<UserDTO> getAllUsers() {
        try {
            Query q = em.createNamedQuery("UserDAO.findAll");
            Collection<UserDAO> result = q.getResultList();
            return userDAOCollectionToUserDTOCollection(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<OrderDTO> getAllOrdersFromAUser(String aUserId) {
        try {
            UserDAO user = findAUser(aUserId);
            Collection<OrderDAO> ordersDAO = user.getOrderDAOCollection();
            Collection<OrderDTO> ordersDTO = new ArrayList<>();
            for (OrderDAO orderDAO : ordersDAO) {
                OrderDTO orderDTO = new OrderDTO(orderDAO.getOrderid(), this.dogDAOCollectionToDogDTOCollection(orderDAO.getDogDAOCollection()));
                ordersDTO.add(orderDTO);
            }
            return ordersDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //used to get session user
    @Override
    public UserDTO getSessionUser(Object aEmail) {
        try {
            // Cast the email object to String
            String email = (String) aEmail;
            Query q = em.createNamedQuery("UserDAO.findByEmail");
            q.setParameter("email", email);
            UserDAO userDAO = (UserDAO) q.getSingleResult();
            // Convert the UserDAO to UserDTO
            UserDTO userDTO = new UserDTO(
                    userDAO.getName(),
                    userDAO.getEmail(),
                    userDAO.getPhone(),
                    userDAO.getAddress(),
                    userDAO.getAppgroup(),
                    userDAO.getUserid(),
                    userDAO.getActive());
            return userDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
