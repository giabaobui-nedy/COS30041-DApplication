package entity;

import entity.OrderDAO;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2023-05-25T17:04:28")
@StaticMetamodel(DogDAO.class)
public class DogDAO_ { 

    public static volatile SingularAttribute<DogDAO, Integer> dogid;
    public static volatile SingularAttribute<DogDAO, Boolean> isavailable;
    public static volatile SingularAttribute<DogDAO, BigDecimal> price;
    public static volatile SingularAttribute<DogDAO, String> name;
    public static volatile SingularAttribute<DogDAO, String> description;
    public static volatile SingularAttribute<DogDAO, Boolean> issold;
    public static volatile CollectionAttribute<DogDAO, OrderDAO> orderDAOCollection;
    public static volatile SingularAttribute<DogDAO, String> breed;
    public static volatile SingularAttribute<DogDAO, String> characteristic;
    public static volatile SingularAttribute<DogDAO, Integer> age;

}