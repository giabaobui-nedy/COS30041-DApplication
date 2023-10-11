/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author giabaobui
 */
@Entity
@Table(name = "DDOG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DogDAO.findAll", query = "SELECT d FROM DogDAO d"),
    @NamedQuery(name = "DogDAO.findByDogid", query = "SELECT d FROM DogDAO d WHERE d.dogid = :dogid"),
    @NamedQuery(name = "DogDAO.findByName", query = "SELECT d FROM DogDAO d WHERE d.name = :name"),
    @NamedQuery(name = "DogDAO.findByBreed", query = "SELECT d FROM DogDAO d WHERE d.breed = :breed"),
    @NamedQuery(name = "DogDAO.findByAge", query = "SELECT d FROM DogDAO d WHERE d.age = :age"),
    @NamedQuery(name = "DogDAO.findByCharacteristic", query = "SELECT d FROM DogDAO d WHERE d.characteristic = :characteristic"),
    @NamedQuery(name = "DogDAO.findByPrice", query = "SELECT d FROM DogDAO d WHERE d.price = :price"),
    @NamedQuery(name = "DogDAO.findByIsavailable", query = "SELECT d FROM DogDAO d WHERE d.isavailable = :isavailable"),
    @NamedQuery(name = "DogDAO.findByIssold", query = "SELECT d FROM DogDAO d WHERE d.issold = :issold"),
    @NamedQuery(name = "DogDAO.findByDescription", query = "SELECT d FROM DogDAO d WHERE d.description = :description")})
public class DogDAO implements Serializable {

    @Size(max = 10)
    @Column(name = "NAME")
    private String name;
    @Size(max = 30)
    @Column(name = "BREED")
    private String breed;
    @Size(max = 30)
    @Column(name = "CHARACTERISTIC")
    private String characteristic;
    @Size(max = 1000)
    @Column(name = "DESCRIPTION")
    private String description;
    @ManyToMany(mappedBy = "dogDAOCollection")
    private Collection<OrderDAO> orderDAOCollection;
    //primary key
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DOGID")
    private Integer dogid;
    @Column(name = "AGE")
    private Integer age;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRICE")
    private BigDecimal price;
    @Column(name = "ISAVAILABLE")
    private Boolean isavailable;
    @Column(name = "ISSOLD")
    private Boolean issold;

    public DogDAO() {
    }

    public DogDAO(Integer dogid) {
        this.dogid = dogid;
    }

    public Integer getDogid() {
        return dogid;
    }

    public void setDogid(Integer dogid) {
        this.dogid = dogid;
    }


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getIsavailable() {
        return isavailable;
    }

    public void setIsavailable(Boolean isavailable) {
        this.isavailable = isavailable;
    }

    public Boolean getIssold() {
        return issold;
    }

    public void setIssold(Boolean issold) {
        this.issold = issold;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dogid != null ? dogid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DogDAO)) {
            return false;
        }
        DogDAO other = (DogDAO) object;
        if ((this.dogid == null && other.dogid != null) || (this.dogid != null && !this.dogid.equals(other.dogid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DogDAO[ dogid=" + dogid + " ]";
    }
    @XmlTransient
    public Collection<OrderDAO> getOrderDAOCollection() {
        return orderDAOCollection;
    }
    public void setOrderDAOCollection(Collection<OrderDAO> orderDAOCollection) {
        this.orderDAOCollection = orderDAOCollection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
