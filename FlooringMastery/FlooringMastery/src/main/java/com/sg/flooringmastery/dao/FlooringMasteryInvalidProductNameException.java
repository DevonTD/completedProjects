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
public class FlooringMasteryInvalidProductNameException extends Exception {

    public FlooringMasteryInvalidProductNameException(String msg) {
        super(msg);
    }

    public FlooringMasteryInvalidProductNameException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
