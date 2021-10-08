/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryInvalidAreaInputException;
import com.sg.flooringmastery.dao.FlooringMasteryInvalidCustomerNameException;
import com.sg.flooringmastery.dao.FlooringMasteryInvalidDateException;
import com.sg.flooringmastery.dao.FlooringMasteryInvalidProductNameException;
import com.sg.flooringmastery.dao.FlooringMasteryInvalidStateNameException;
import com.sg.flooringmastery.dao.FlooringMasteryNoOrderException;
import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dto.Order;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author devon
 */
public interface FlooringMasteryService {

    // Generic methods for writing to, updating or grabbing objects from memory
    List<Order> displayOrdersByDate(String date) throws FlooringMasteryNoOrderException, FlooringMasteryPersistenceException,
            FlooringMasteryInvalidDateException;

    Order addOrder(Order order) throws FlooringMasteryPersistenceException, FlooringMasteryNoOrderException, IOException,
            FlooringMasteryInvalidDateException, FlooringMasteryInvalidAreaInputException,
            FlooringMasteryInvalidStateNameException, FlooringMasteryInvalidCustomerNameException,
            FlooringMasteryInvalidProductNameException;

    Order updateOrder(Order order, Order updated) throws FlooringMasteryNoOrderException, FlooringMasteryPersistenceException,
            FlooringMasteryInvalidDateException, FlooringMasteryInvalidAreaInputException,
            FlooringMasteryInvalidStateNameException, FlooringMasteryInvalidCustomerNameException,
            FlooringMasteryInvalidProductNameException, IOException;

    Order removeOrder(Order order) throws FlooringMasteryNoOrderException, FlooringMasteryPersistenceException,
            FlooringMasteryInvalidDateException;

    Order getOrderByDate(String date, int number)
            throws FlooringMasteryPersistenceException,
            FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException;

    // All Calculations
    BigDecimal materialCost(BigDecimal area, BigDecimal costPerSq);

    BigDecimal laborCost(BigDecimal area, BigDecimal laborCostPerSq);

    BigDecimal tax(BigDecimal materialCost, BigDecimal laborCost, Order order);

    BigDecimal total(BigDecimal materialCost, BigDecimal laborCost, BigDecimal tax);

    // Export 
    void exportData(List<String> myList)throws FileNotFoundException, FlooringMasteryPersistenceException, 
            FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException;

    List<String> getFileNames();

}
