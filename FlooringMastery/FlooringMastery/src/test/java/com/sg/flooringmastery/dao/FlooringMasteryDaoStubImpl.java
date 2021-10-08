/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author devon
 */
public class FlooringMasteryDaoStubImpl implements OrderDao {

    Order currentOrder;

    public FlooringMasteryDaoStubImpl() {

    }

    public FlooringMasteryDaoStubImpl(Order testOrder) {
        this.currentOrder = testOrder;
    }

    @Override
    public Order addOrder(Order order)
            throws FlooringMasteryPersistenceException,
            FlooringMasteryNoOrderException, IOException, FlooringMasteryInvalidDateException {
        if (order.getOrderNumber() != currentOrder.getOrderNumber()) {
            return null;
        } else {
            return currentOrder;
        }
    }

    @Override
    public List<Order> getOrderListByDate(String date)
            throws FlooringMasteryPersistenceException,
            FlooringMasteryNoOrderException {
        List<Order> thisList = new ArrayList<>();
        return thisList;
    }

    @Override
    public Order getOrderByDate(String date, int number)
            throws FlooringMasteryPersistenceException,
            FlooringMasteryNoOrderException {
        if (!date.equals(currentOrder.getOrderDate()) && number != currentOrder.getOrderNumber()) {
            return null;
        } else {
            return currentOrder;
        }
    }

    @Override
    public Order updateOrder(Order order, Order updated)
            throws FlooringMasteryPersistenceException, FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException {
        if (!order.getOrderDate().equals(currentOrder.getOrderDate()) && order != currentOrder && order.getOrderNumber() != currentOrder.getOrderNumber()) {
            return null;
        } else {
            return currentOrder;
        }
    }

    @Override
    public Order removeOrder(Order order)
            throws FlooringMasteryPersistenceException,
            FlooringMasteryNoOrderException {
        if (order.getOrderDate().equals(currentOrder.getOrderDate()) && order.getOrderNumber() == currentOrder.getOrderNumber()) {
            return currentOrder;
        } else {
            return null;
        }
    }

    @Override
    public List<String> getFileNames() {
        List<String> myList = new ArrayList<>();
        File dir = new File("C:/Repo/online-Java-2019-DevonTD/Summatives/M5 Summative/FlooringMastery");
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith("order");
            }
        };
        File[] files = dir.listFiles(filter);
        for (int i = 0; i < files.length; i++) {
            String myString = files[i].getName();
            String orderRemoved = myString.replace("order_", "");
            String newString = orderRemoved.replace(".txt", "");
            myList.add(newString);
        }
        return myList;
    }
}
