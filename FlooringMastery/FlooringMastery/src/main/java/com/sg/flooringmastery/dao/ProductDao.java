/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.util.List;

/**
 *
 * @author devon
 */
public interface ProductDao {
    /**
     * 
     * @param key
     * @return returns a products price based off it's name
     * @throws com.sg.flooringmastery.dao.FlooringMasteryPersistenceException
     */
    Product getProduct(String key) 
            throws FlooringMasteryPersistenceException;

    
    /**
     *
     * @return
     * @throws com.sg.flooringmastery.dao.FlooringMasteryPersistenceException
     */
    List<Product> displayProduct() 
            throws FlooringMasteryPersistenceException;
}
