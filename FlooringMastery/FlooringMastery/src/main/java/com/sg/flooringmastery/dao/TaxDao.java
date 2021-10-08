/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;
import java.util.List;

/**
 *
 * @author devon
 */
public interface TaxDao {

    /**
     *
     * @param key
     * @return
     * @throws FlooringMasteryPersistenceException
     */
    Tax getTax(String key) 
            throws FlooringMasteryPersistenceException;
    
    /**
     * 
     * @return
     * @throws FlooringMasteryPersistenceException 
     */
    List<Tax> displayTax() throws FlooringMasteryPersistenceException;
}
