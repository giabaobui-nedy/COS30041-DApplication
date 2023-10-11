/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import DTOs.DogDTO;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import session.EDDogsHavenBLLFacadeRemote;

/**
 *
 * @author giabaobui
 */
@Named(value = "dogBean")
@RequestScoped
public class DogBean {

    @EJB
    private EDDogsHavenBLLFacadeRemote bll;

    private Object dogId;
    private String name;
    private String breed;
    private String characteristic;
    private String description;
    private Integer age;
    private BigDecimal price;
    private boolean isAvailable;
    private boolean isSold;

    /**
     * Creates a new instance of dogBean
     */
    public DogBean() {
        Map<String, String> requestParams = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (requestParams.containsKey("dogId")) {
            initializeADog(requestParams);
        } else {
            dogId = null;
            name = null;
            breed = null;
            characteristic = null;
            description = null;
            age = null;
            price = null;
            isAvailable = true;
            isSold = false;
        }
    }

    private void initializeADog(Map<String, String> requestParams) {
        dogId = requestParams.get("dogId");
        name = requestParams.get("name");
        breed = requestParams.get("breed");
        characteristic = requestParams.get("characteristic");
        description = requestParams.get("description");
        age = Integer.parseInt(requestParams.get("age"));
        price = new BigDecimal(requestParams.get("price"));
        isAvailable = Boolean.parseBoolean(requestParams.get("isAvailable"));
        isSold = Boolean.parseBoolean(requestParams.get("isSold"));
    }

    public Collection<DogDTO> getAllDogsForCustomer() {
        try {
            return bll.getAllAvailableDogs();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Collection<DogDTO> getAllDogsForAdmin() {
        try {
            return bll.getAllDogs();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String addADog() {
        try {
            DogDTO lDog = new DogDTO(name, breed, characteristic, description, age, price);
            if (bll.addADog(lDog)) {
                return "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return "failure";
    }

    public String updateADog() {
        try {
            DogDTO lUpdatedDog = new DogDTO(name, breed, characteristic, description, Integer.parseInt(dogId.toString()), age, price, isAvailable, isSold);
            if (bll.updateADog(lUpdatedDog)) {
                return "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "failure";
    }

    public Object getDogId() {
        return dogId;
    }

    public void setDogId(Object dogId) {
        this.dogId = dogId;
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

    public boolean isIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public boolean isIsSold() {
        return isSold;
    }

    public void setIsSold(boolean isSold) {
        this.isSold = isSold;
    }

}
