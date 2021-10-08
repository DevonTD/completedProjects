/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery;

import com.sg.flooringmastery.controller.FlooringMasteryController;
import com.sg.flooringmastery.dao.FlooringMasteryInvalidAreaInputException;
import com.sg.flooringmastery.dao.FlooringMasteryInvalidCustomerNameException;
import com.sg.flooringmastery.dao.FlooringMasteryInvalidDateException;
import com.sg.flooringmastery.dao.FlooringMasteryInvalidProductNameException;
import com.sg.flooringmastery.dao.FlooringMasteryInvalidStateNameException;
import com.sg.flooringmastery.dao.FlooringMasteryNoOrderException;
import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import java.io.IOException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author devon
 */
public class app {

    public static void main(String[] args) throws FlooringMasteryPersistenceException, FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException,
            FlooringMasteryInvalidAreaInputException, FlooringMasteryInvalidStateNameException, FlooringMasteryInvalidCustomerNameException,
            FlooringMasteryInvalidProductNameException, IOException {
//        FlooringMasteryController controller = new FlooringMasteryController();
//        controller.run();
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        FlooringMasteryController controller = ctx.getBean("controller", FlooringMasteryController.class);
        controller.run(); 
    }
}// This line is meaningless.