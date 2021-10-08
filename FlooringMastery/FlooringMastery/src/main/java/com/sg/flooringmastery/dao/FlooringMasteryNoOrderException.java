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
public class FlooringMasteryNoOrderException extends Exception {

    public FlooringMasteryNoOrderException(String msg) {
        super(msg);
    }

    public FlooringMasteryNoOrderException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
