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
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author devon
 */
public class FlooringMasteryServiceImplTest {

    FlooringMasteryService service;

    public FlooringMasteryServiceImplTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("serviceLayer", FlooringMasteryService.class);
    }

    //@Test 
    public void testAddOrder() throws FlooringMasteryInvalidProductNameException, FlooringMasteryInvalidCustomerNameException,
            FlooringMasteryInvalidAreaInputException, IOException, FlooringMasteryPersistenceException,
            FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException,
            FlooringMasteryInvalidStateNameException {
        // Create Object
        Order testOrder = new Order();
        testOrder.setOrderDate("03-24-2021");
        testOrder.setCustomerName("Devon Dixon - LLC");
        testOrder.setProductType("Carpet");
        testOrder.setStateName("MN");
        testOrder.setArea(new BigDecimal("12.5"));

        // Create second Object
        Order testOrderTwo = new Order();
        testOrderTwo.setOrderDate("03-24-2021");
        testOrderTwo.setCustomerName("Gulie-Gulie");
        testOrderTwo.setProductType("Tile");
        testOrderTwo.setStateName("TX");
        testOrderTwo.setArea(new BigDecimal("81.12"));

        // Add object to collection
        service.addOrder(testOrder);
        service.addOrder(testOrderTwo);

        // Retrieve object 
        Order order = service.getOrderByDate(testOrder.getOrderDate(), testOrder.getOrderNumber());

        // Make assertions
        assertEquals(order.getCustomerName(), testOrder.getCustomerName(), "Devon Dixon, LLC");
        assertEquals(order.getOrderDate(), testOrder.getOrderDate(), "03-24-2021");
        assertEquals(testOrder.getProductType(), order.getProductType(), "Carpet");
        assertEquals(order.getStateName(), testOrder.getStateName(), "MN");
        assertEquals(order.getArea(), testOrder.getArea());
        // Cost of the particular product
        assertEquals(order.getCostPerSqFt(), new BigDecimal("2.25"));
        assertEquals(order.getLaborCostPerSqFt(), new BigDecimal("2.10"));
        // State's Tax
        assertEquals(order.getTaxRate(), new BigDecimal("4.25"));
    }

    //@Test  
    public void testUpdateOrder() throws FlooringMasteryPersistenceException, IOException, FlooringMasteryNoOrderException,
            FlooringMasteryInvalidDateException, FlooringMasteryInvalidAreaInputException, FlooringMasteryInvalidStateNameException,
            FlooringMasteryInvalidCustomerNameException, FlooringMasteryInvalidProductNameException {
        // Create Object
        Order testOrder = new Order();
        testOrder.setOrderDate("03-25-2021");
        testOrder.setCustomerName("Devon Dixon - LLC");
        testOrder.setProductType("Carpet");
        testOrder.setStateName("MN");
        testOrder.setArea(new BigDecimal("12.5"));

        // Add object to collection
        service.addOrder(testOrder);
        Order thisOrder = service.getOrderByDate(testOrder.getOrderDate(), testOrder.getOrderNumber());

        // Create new Object
        Order updatedOrder = new Order();
        updatedOrder.setOrderDate("03-25-2021");
        updatedOrder.setCustomerName("Billy Joel");
        updatedOrder.setProductType("Tile");
        updatedOrder.setStateName("CA");
        updatedOrder.setArea(new BigDecimal("12.5"));
        
        service.updateOrder(thisOrder, updatedOrder);
        
        Order newAndImproved = service.getOrderByDate(testOrder.getOrderDate(), testOrder.getOrderNumber());

        // Make assertions
        assertEquals(updatedOrder.getCustomerName(), newAndImproved.getCustomerName(), "Billy Joel");
        assertEquals(updatedOrder.getOrderDate(), newAndImproved.getOrderDate(), "03-25-2021");
        assertEquals(updatedOrder.getProductType(), newAndImproved.getProductType(), "Tile");
        assertEquals(updatedOrder.getStateName(), newAndImproved.getStateName(), "CA");
        assertEquals(updatedOrder.getArea(), newAndImproved.getArea());
        // Cost of the particular product
        assertEquals(newAndImproved.getCostPerSqFt(), new BigDecimal("3.50"));
        assertEquals(newAndImproved.getLaborCostPerSqFt(), new BigDecimal("4.15"));
        // State's Tax
        assertEquals(newAndImproved.getTaxRate(), new BigDecimal("25.70"));
    }

    //@Test 
    public void testDisplayAllOrdersByDate() throws IOException, FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException,
            FlooringMasteryInvalidAreaInputException, FlooringMasteryPersistenceException, FlooringMasteryInvalidStateNameException,
            FlooringMasteryInvalidCustomerNameException, FlooringMasteryInvalidProductNameException {
        // Create Object
        Order firstOrder = new Order();
        firstOrder.setOrderDate("03-23-2021");
        firstOrder.setCustomerName("Devon Dixon - LLC");
        firstOrder.setProductType("Carpet");
        firstOrder.setStateName("MN");
        firstOrder.setArea(new BigDecimal("12.5"));

        // Create new Object
        Order secondOrder = new Order();
        secondOrder.setOrderDate("03-24-2021");
        secondOrder.setCustomerName("Steve Rogers");
        secondOrder.setProductType("Brick");
        secondOrder.setStateName("TX");
        secondOrder.setArea(new BigDecimal("50.14"));

        service.addOrder(firstOrder);
        service.addOrder(secondOrder);

        List<Order> firstList = service.displayOrdersByDate(firstOrder.getOrderDate());

        assertNotNull(firstList, "List should not be null");
        assertEquals(1, firstList.size(), "Should contain only one object");

        assertTrue(service.displayOrdersByDate(firstOrder.getOrderDate()).contains(firstOrder));

        List<Order> secondList = service.displayOrdersByDate(secondOrder.getOrderDate());

        assertNotNull(secondList, "List should not be null");
        assertEquals(1, secondList.size(), "Should contain only one object");

        assertTrue(service.displayOrdersByDate(secondOrder.getOrderDate()).contains(secondOrder));

    }

    //@Test
    public void testRemoveOrder() throws FlooringMasteryPersistenceException, FlooringMasteryNoOrderException, IOException,
            FlooringMasteryInvalidDateException, FlooringMasteryInvalidAreaInputException, FlooringMasteryInvalidStateNameException,
            FlooringMasteryInvalidCustomerNameException, FlooringMasteryInvalidProductNameException {
        // Create object
        // Create Object
        Order testOrder = new Order();
        testOrder.setOrderDate("03-28-2021");
        testOrder.setCustomerName("Devon Dixon - LLC");
        testOrder.setProductType("Carpet");
        testOrder.setStateName("MN");
        testOrder.setArea(new BigDecimal("12.5"));

        // Create second Object
        Order testOrderTwo = new Order();
        testOrderTwo.setOrderDate("03-28-2021");
        testOrderTwo.setCustomerName("Gulie-Gulie");
        testOrderTwo.setProductType("Tile");
        testOrderTwo.setStateName("TX");
        testOrderTwo.setArea(new BigDecimal("81.12"));

        // Add object to dao
        service.addOrder(testOrder);
        service.addOrder(testOrderTwo);

        // Get object from dao to compare results
        Order order = service.getOrderByDate(testOrder.getOrderDate(), 1);
        Order orderTwo = service.getOrderByDate(testOrderTwo.getOrderDate(), 2);

        assertEquals(testOrder.getCustomerName(), order.getCustomerName(), "Devon");
        assertEquals(testOrder.getOrderNumber(), order.getOrderNumber(), 1);
        assertEquals(testOrder.getArea(), order.getArea(), "4.00");
        assertEquals(testOrder.getOrderDate(), order.getOrderDate(), "03-28-2021");
        assertEquals(testOrder.getProductType(), order.getProductType(), "Carpet");

        assertEquals(testOrderTwo.getOrderNumber(), orderTwo.getOrderNumber(), 2);
        assertEquals(testOrderTwo.getArea(), orderTwo.getArea(), "6.20");
        assertEquals(testOrderTwo.getOrderDate(), orderTwo.getOrderDate(), "03-28-2021");
        assertEquals(testOrderTwo.getProductType(), orderTwo.getProductType(), "Lumber");
        assertEquals(testOrderTwo.getCustomerName(), orderTwo.getCustomerName(), "Hail");

        // Remove testOrderOne
        Order removed = service.removeOrder(testOrder);

        assertEquals(testOrder, removed, "These two are one and the same");
    }

    //@Test // Delete all files before running this method. Also Clean and Build, just incase
    public void testGetAllOrders() throws FlooringMasteryPersistenceException, FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException,
            IOException, FlooringMasteryInvalidAreaInputException, FlooringMasteryInvalidStateNameException,
            FlooringMasteryInvalidProductNameException, FlooringMasteryInvalidCustomerNameException {
        // Create object
        Order testOrder = new Order();
        testOrder.setOrderDate("03-30-2021");
        testOrder.setCustomerName("Devon Dixon - LLC");
        testOrder.setProductType("Carpet");
        testOrder.setStateName("MN");
        testOrder.setArea(new BigDecimal("12.5"));

        // Create second Object
        Order testOrderTwo = new Order();
        testOrderTwo.setOrderDate("03-26-2021");
        testOrderTwo.setCustomerName("Gulie-Gulie");
        testOrderTwo.setProductType("Tile");
        testOrderTwo.setStateName("TX");
        testOrderTwo.setArea(new BigDecimal("81.12"));

        // Create second object
        Order testOrderThree = new Order();
        testOrderThree.setOrderDate("03-30-2021");
        testOrderThree.setCustomerName("Me");
        testOrderThree.setProductType("Tile");
        testOrderThree.setStateName("MN");
        testOrderThree.setArea(new BigDecimal("100"));

        // Create third object
        Order testOrderFour = new Order();
        testOrderFour.setOrderDate("03-27-2021");
        testOrderFour.setCustomerName("Maddie");
        testOrderFour.setProductType("Brick");
        testOrderFour.setStateName("CA");
        testOrderFour.setArea(new BigDecimal("852"));

        // Add object to dao
        service.addOrder(testOrder);
        service.addOrder(testOrderTwo);
        service.addOrder(testOrderThree);
        service.addOrder(testOrderFour);

        List<String> myList = service.getFileNames();

        assertNotNull(myList, "List Should not be null");
        assertEquals(3, myList.size(), "List size should only contain one element");
        assertEquals(myList.get(0), "03-26-2021", "Should be the same");
        assertEquals(myList.get(1), "03-27-2021", "Should be the same");
      
        service.exportData(myList);
    }
}
