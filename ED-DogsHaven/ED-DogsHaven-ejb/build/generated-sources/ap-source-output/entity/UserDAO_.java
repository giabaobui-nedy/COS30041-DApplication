package entity;

import entity.OrderDAO;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2023-05-25T17:04:28")
@StaticMetamodel(UserDAO.class)
public class UserDAO_ { 

    public static volatile SingularAttribute<UserDAO, String> password;
    public static volatile SingularAttribute<UserDAO, String> address;
    public static volatile SingularAttribute<UserDAO, String> phone;
    public static volatile SingularAttribute<UserDAO, String> appgroup;
    public static volatile SingularAttribute<UserDAO, String> name;
    public static volatile SingularAttribute<UserDAO, Boolean> active;
    public static volatile CollectionAttribute<UserDAO, OrderDAO> orderDAOCollection;
    public static volatile SingularAttribute<UserDAO, String> userid;
    public static volatile SingularAttribute<UserDAO, String> email;

}