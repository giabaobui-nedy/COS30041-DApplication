package entity;

import entity.DogDAO;
import entity.UserDAO;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2023-05-25T17:04:28")
@StaticMetamodel(OrderDAO.class)
public class OrderDAO_ { 

    public static volatile SingularAttribute<OrderDAO, Integer> orderid;
    public static volatile SingularAttribute<OrderDAO, UserDAO> customerid;
    public static volatile CollectionAttribute<OrderDAO, DogDAO> dogDAOCollection;

}