/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author giabaobui
 */
@Entity
@Table(name = "DORDER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderDAO.findAll", query = "SELECT o FROM OrderDAO o"),
    @NamedQuery(name = "OrderDAO.findByOrderid", query = "SELECT o FROM OrderDAO o WHERE o.orderid = :orderid")})
public class OrderDAO implements Serializable {

    @JoinTable(name = "DORDERITEM", joinColumns = {
        @JoinColumn(name = "ORDERID", referencedColumnName = "ORDERID")}, inverseJoinColumns = {
        @JoinColumn(name = "DOGID", referencedColumnName = "DOGID")})
    @ManyToMany
    private Collection<DogDAO> dogDAOCollection;
    //primary key
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ORDERID")
    private Integer orderid;
    @JoinColumn(name = "CUSTOMERID", referencedColumnName = "USERID")
    @ManyToOne
    private UserDAO customerid;

    public OrderDAO() {
    }

    public OrderDAO(Integer orderid) {
        this.orderid = orderid;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public UserDAO getCustomerid() {
        return customerid;
    }

    public void setCustomerid(UserDAO customerid) {
        this.customerid = customerid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderid != null ? orderid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderDAO)) {
            return false;
        }
        OrderDAO other = (OrderDAO) object;
        if ((this.orderid == null && other.orderid != null) || (this.orderid != null && !this.orderid.equals(other.orderid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.OrderDAO[ orderid=" + orderid + " ]";
    }

    @XmlTransient
    public Collection<DogDAO> getDogDAOCollection() {
        return dogDAOCollection;
    }

    public void setDogDAOCollection(Collection<DogDAO> dogDAOCollection) {
        this.dogDAOCollection = dogDAOCollection;
    }
    
}
