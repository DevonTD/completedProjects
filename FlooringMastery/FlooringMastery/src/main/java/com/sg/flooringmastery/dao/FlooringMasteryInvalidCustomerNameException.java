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
public class FlooringMasteryInvalidCustomerNameException extends Exception {
    
    public FlooringMasteryInvalidCustomerNameException(String msg){
        super(msg);
    }
    
    public FlooringMasteryInvalidCustomerNameException(String msg, Throwable cause){
        super(msg, cause);
    }
    
}
