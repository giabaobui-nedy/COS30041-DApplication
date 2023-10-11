/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import DTOs.DogDTO;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.Remove;
import javax.ejb.Stateful;

/**
 *
 * @author giabaobui
 */
@Stateful
public class Cart implements CartRemote {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private Collection<DogDTO> cart;

    public Cart() {
        cart = new ArrayList<>();
    }

    @Override
    public boolean addItem(DogDTO aDog) {
        try {
            if (aDog != null) {
                if (cart.isEmpty()) {
                    return cart.add(aDog);
                } else {
                    for (DogDTO dog : cart) {
                        if (aDog.getDogId().equals(dog.getDogId())) {
                            break;
                        }
                        return cart.add(aDog);
                    }
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeItem(Integer aDogId) {
        try {
            if (aDogId != null) {
                for (DogDTO dog : cart) {
                    if (aDogId.equals(dog.getDogId())) {
                        return cart.remove(dog);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Collection<DogDTO> getCart() {
        return cart;
    }

    @Remove
    public void removeCart() {
        cart = null;
    }

    @Override
    public void emptyCart() {
        cart = new ArrayList<>();
    }

    @Override
    public boolean isEmpty() {
        return cart.isEmpty();
    }
}
