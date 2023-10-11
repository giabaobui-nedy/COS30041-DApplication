/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import DTOs.DogDTO;
import DTOs.OrderDTO;
import java.util.Collection;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import session.EDDogsHavenBLLFacadeRemote;

/**
 *
 * @author giabaobui
 */
@Named(value = "orderBean")
@RequestScoped
public class OrderBean {

    @EJB
    private EDDogsHavenBLLFacadeRemote bll;

    public OrderBean() {
    }

    public Collection<OrderDTO> getAllOrders() {
        try {
            return bll.getAllOrders();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //calculate the total price for the items in an order
    public double getTotalPrice(Collection<DogDTO> aCollection) {
        double totalPrice = 0.0;
        for (DogDTO dog : aCollection) {
            totalPrice += dog.getPrice().doubleValue();
        }
        return totalPrice;
    }
    
    public boolean isOrderResolved(Collection<DogDTO> aCollection) {
        for (DogDTO dog : aCollection) {
            if (!dog.getIsSold()) {
                return false;
            }
        }
        return true;
    }
    
     public String removeAnOrder(Object aOrderId) {
        try {
            if (bll.deleteAnOrder(aOrderId)) {
                return "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "failure";
    }
}
