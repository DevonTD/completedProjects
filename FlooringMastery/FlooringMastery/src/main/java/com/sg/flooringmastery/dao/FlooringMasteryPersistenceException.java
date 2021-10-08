/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

/**
 *
 * @author devon
 */
public class FlooringMasteryPersistenceException extends Exception {

    public FlooringMasteryPersistenceException(String msg) {
        super(msg);
    }
    
    public FlooringMasteryPersistenceException(String msg, Throwable cause){
        super(msg, cause);
    }
}
