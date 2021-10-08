/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author devon
 */
public class OrderDaoImplTest {

    OrderDao testDao;
    public OrderDaoImplTest() {

    }

    @BeforeEach
    public void setUp() throws IOException {
        testDao = new OrderDaoImpl();
    }

    //@Test 
    public void testCreateOrder()
            throws FlooringMasteryNoOrderException,
            FlooringMasteryPersistenceException, IOException, FlooringMasteryInvalidDateException,
            FlooringMasteryInvalidAreaInputException, FlooringMasteryInvalidStateNameException,
            FlooringMasteryInvalidCustomerNameException, FlooringMasteryInvalidProductNameException {
        // Create object
        Order orderOne = new Order();
        orderOne.setCustomerName("Devon");
        orderOne.setOrderNumber(1);
        orderOne.setProductType("Carpet");
        orderOne.setArea(new BigDecimal("4.00"));
        orderOne.setStateName("MN");
        orderOne.setOrderDate("04-10-2021");

        Order orderTwo = new Order();
        orderTwo.setCustomerName("Billy");
        orderTwo.setOrderNumber(1);
        orderTwo.setProductType("Carpet");
        orderTwo.setArea(new BigDecimal("4.00"));
        orderTwo.setStateName("MN");
        orderTwo.setOrderDate("04-10-2021");

        // Add object to dao
        testDao.addOrder(orderOne);
        testDao.addOrder(orderTwo);

        // Get object from dao to compare results
        Order testOrder = testDao.getOrderByDate(orderOne.getOrderDate(), 1);

        assertEquals(orderOne.getCustomerName(), testOrder.getCustomerName(), "Devon");
        assertEquals(orderOne.getOrderNumber(), testOrder.getOrderNumber(), 1);
        assertEquals(orderOne.getArea(), testOrder.getArea(), "4.00");
        assertEquals(orderOne.getOrderDate(), testOrder.getOrderDate(), "04-10-2021");
        assertEquals(orderOne.getProductType(), testOrder.getProductType(), "Carpet");

    }

    // @Test 
    public void testGetOrderListByDate() throws FlooringMasteryNoOrderException,
            FlooringMasteryPersistenceException, IOException, FlooringMasteryInvalidDateException {
        // Create object
        Order testOrderOne = new Order();
        testOrderOne.setCustomerName("Devon");
        testOrderOne.setOrderNumber(1);
        testOrderOne.setProductType("glass");
        testOrderOne.setArea(new BigDecimal("4.00"));
        testOrderOne.setStateName("MN");
        testOrderOne.setOrderDate("04-05-2021");

        Order testOrderTwo = new Order();
        testOrderTwo.setCustomerName("Nathan");
        testOrderTwo.setOrderNumber(2);
        testOrderTwo.setProductType("glass");
        testOrderTwo.setArea(new BigDecimal("6.20"));
        testOrderTwo.setStateName("TX");
        testOrderTwo.setOrderDate("04-05-2021");

        Order testOrderThree = new Order();
        testOrderThree.setCustomerName("Gulie");
        testOrderThree.setOrderNumber(3);
        testOrderThree.setProductType("glass");
        testOrderThree.setArea(new BigDecimal("6.20"));
        testOrderThree.setStateName("TX");
        testOrderThree.setOrderDate("04-05-2021");

        // Add object to dao
        testDao.addOrder(testOrderOne);
        testDao.addOrder(testOrderTwo);
        testDao.addOrder(testOrderThree);

        List<Order> myList = testDao.getOrderListByDate("04-05-2021");

        assertNotNull(myList, "Should not be NULL");
        assertEquals(3, myList.size(), "List size should be 2");

    }

    //@Test
    public void testEditOrder() throws FlooringMasteryPersistenceException, FlooringMasteryNoOrderException, IOException,
            FlooringMasteryInvalidDateException {
        // Create object
        Order oldOrder = new Order();
        oldOrder.setCustomerName("Devon");
        oldOrder.setProductType("Carpet");
        oldOrder.setArea(new BigDecimal("100"));
        oldOrder.setStateName("MN");
        oldOrder.setOrderDate("04-05-2021");

        // Add object to dao
        testDao.addOrder(oldOrder);
        Order thisOrder = testDao.getOrderByDate(oldOrder.getOrderDate(), oldOrder.getOrderNumber());

        // Create new object to replace oldOrder one
        Order updated = new Order();
        updated.setCustomerName("Billy");
        updated.setProductType("Wood");
        updated.setArea(new BigDecimal("120"));
        updated.setStateName("TX");
        updated.setOrderDate("04-05-2021");

        testDao.updateOrder(thisOrder, updated);

        Order newOrder = testDao.getOrderByDate(oldOrder.getOrderDate(), oldOrder.getOrderNumber());

        assertEquals(updated.getCustomerName(), newOrder.getCustomerName(), "Name should be Billy");
        assertEquals(updated.getProductType(), newOrder.getProductType(), "Should be Wood");
        assertEquals(updated.getStateName(), newOrder.getStateName(), "Should be TX");
        assertEquals(updated.getArea(), newOrder.getArea(), "Should be 120");
    }

    //@Test 
    public void testRemoveOrder()
            throws FlooringMasteryPersistenceException,
            IOException, FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException {
        // Create object
        Order orderOne = new Order();
        orderOne.setCustomerName("Devon");
        orderOne.setProductType("Carpet");
        orderOne.setArea(new BigDecimal("4.00"));
        orderOne.setStateName("ND");
        orderOne.setOrderDate("04-05-2021");

        // create second object
        Order orderTwo = new Order();
        orderTwo.setCustomerName("Hail");
        orderTwo.setProductType("Lumber");
        orderTwo.setArea(new BigDecimal("6.20"));
        orderTwo.setStateName("TX");
        orderTwo.setOrderDate("04-05-2021");

        // Add object to dao
        testDao.addOrder(orderOne);
        testDao.addOrder(orderTwo);

        // Get object from dao to compare results
        Order order = testDao.getOrderByDate(orderOne.getOrderDate(), orderOne.getOrderNumber());
        Order testOrder = testDao.getOrderByDate(orderTwo.getOrderDate(), orderTwo.getOrderNumber());

        assertEquals(orderOne.getCustomerName(), order.getCustomerName(), "Devon");
        assertEquals(orderOne.getArea(), order.getArea(), "4.00");
        assertEquals(orderOne.getOrderDate(), order.getOrderDate(), "03-05-2021");
        assertEquals(orderOne.getProductType(), order.getProductType(), "Carpet");

        assertEquals(orderTwo.getArea(), testOrder.getArea(), "6.20");
        assertEquals(orderTwo.getOrderDate(), testOrder.getOrderDate(), "03-05-2021");
        assertEquals(orderTwo.getProductType(), testOrder.getProductType(), "Lumber");
        assertEquals(orderTwo.getCustomerName(), testOrder.getCustomerName(), "Hail");

        // Remove testOrderOne
        Order removed = testDao.removeOrder(orderTwo);

        assertEquals(orderTwo, removed, "These two are one and the same");

        // Check object list to see if the order has been truly removed
        List<Order> myList = testDao.getOrderListByDate("04-05-2021");

        assertNotNull(myList, "Should not be NULL");
        assertEquals(1, myList.size(), "List size should be 1");
    }

    //@Test
    public void testGetOrderByDate() throws FlooringMasteryPersistenceException, FlooringMasteryNoOrderException,
            IOException, FlooringMasteryInvalidDateException {
        // Create object
        Order testOrderOne = new Order();
        testOrderOne.setCustomerName("Devon");
        testOrderOne.setOrderNumber(1);
        testOrderOne.setProductType("Carpet");
        testOrderOne.setArea(new BigDecimal("4.00"));
        testOrderOne.setStateName("MN");
        testOrderOne.setOrderDate("04-05-2021");

        // Create second testOrderOne object
        Order testOrderTwo = new Order();
        testOrderTwo.setCustomerName("Hail");
        testOrderTwo.setOrderNumber(1);
        testOrderTwo.setProductType("Lumber");
        testOrderTwo.setArea(new BigDecimal("6.20"));
        testOrderTwo.setStateName("TX");
        testOrderTwo.setOrderDate("04-06-2021");

        // Add object to dao
        testDao.addOrder(testOrderOne);
        testDao.addOrder(testOrderTwo);

        // Grab both testOrderOne objects by their date and testOrderOne number
        Order test = testDao.getOrderByDate(testOrderOne.getOrderDate(), testOrderOne.getOrderNumber());

        assertEquals(testOrderOne.getOrderDate(), test.getOrderDate(), "Dates should both be 04-05-2021");
        assertEquals(testOrderOne.getArea(), test.getArea(), "Area should both be 4.00");

        Order testTwo = testDao.getOrderByDate(testOrderTwo.getOrderDate(), testOrderTwo.getOrderNumber());

        assertEquals(testTwo.getOrderDate(), testOrderTwo.getOrderDate(), "Dates should both be 04-06-2021");
        assertEquals(testOrderTwo.getArea(), testTwo.getArea(), "Area should be 6.20");

    }
}
