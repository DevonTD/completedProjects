/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Scanner;

/**
 *
 * @author devon
 */
public class OrderDaoImpl implements OrderDao {

    public static final String DELIMITER = ",";
    private Map<Integer, Order> orderMap;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    private String createFileName(String date) throws FlooringMasteryInvalidDateException {
        LocalDate fileName = LocalDate.parse(date, formatter);
        if (fileName.isBefore(LocalDate.now())) {
            throw new FlooringMasteryInvalidDateException("--- PLEASE ENTER A VALID DATE ---");
        } else {
            String formattedFileName = fileName.format(formatter);
            return "order_" + formattedFileName + ".txt";
        }
    } // Creates a new file for every different date inputted

    public OrderDaoImpl() {
        this.orderMap = new HashMap<>();
    } // Constructor used for instantiating the map

    @Override
    public Order addOrder(Order order) throws FlooringMasteryPersistenceException, FlooringMasteryNoOrderException,
            IOException, FlooringMasteryInvalidDateException {

        if (checkIfExists(order) == true) {
            increment(order);
        } else if (checkIfExists(order) == false) {
            orderMap = new HashMap<>();
        }
        orderMap.put(order.getOrderNumber(), order);
        writeToFile(order.getOrderDate());
        return order;
    }

    @Override
    public List<Order> getOrderListByDate(String date)
            throws FlooringMasteryPersistenceException, FlooringMasteryNoOrderException,
            FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException {
        // Retrieves a list of orders based of of the inputted parameter

        try {
            loadFromFile(date);
        } catch (FlooringMasteryNoOrderException e) {
            throw new FlooringMasteryNoOrderException("There are no orders matching the input saved in this file.", e);

        } catch (FlooringMasteryInvalidDateException ex) {
            throw new FlooringMasteryInvalidDateException("--- PLEASE ENTER A VALID DATE ---");
        }
        return new ArrayList<>(orderMap.values());
    }

    @Override
    public Order getOrderByDate(String date, int number)
            throws FlooringMasteryPersistenceException, FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException {
        // Retrieves an order based off of the date and number associated with it.

        try {
            loadFromFile(date);
        } catch (FlooringMasteryNoOrderException e) {
            throw new FlooringMasteryNoOrderException("There are no orders matching the input saved in this file.", e);
        } catch (FlooringMasteryInvalidDateException ex) {
            throw new FlooringMasteryInvalidDateException("--- PLEASE ENTER A VALID DATE ---");
        }

        Order thisOrder = orderMap.get(number);

        return thisOrder;
    }

    @Override
    public Order updateOrder(Order order, Order updated)
            throws FlooringMasteryPersistenceException, FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException {
        // Retrieves and edits an order based off of the date and number associated with it.
        try {
            loadFromFile(order.getOrderDate());
        } catch (FlooringMasteryNoOrderException e) {
            throw new FlooringMasteryNoOrderException("There are no orders matching the input saved in this file.", e);
        } catch (FlooringMasteryInvalidDateException ex) {
            throw new FlooringMasteryInvalidDateException("--- PLEASE ENTER A VALID DATE ---");
        }

        Order newOrder = orderMap.replace(order.getOrderNumber(), updated);
        try {
            writeToFile(updated.getOrderDate());
        } catch (FlooringMasteryInvalidDateException ex) {
            throw new FlooringMasteryInvalidDateException("--- PLEASE ENTER A VALID DATE ---");
        }

        return newOrder;
    }

    @Override
    public Order removeOrder(Order order)
            throws FlooringMasteryPersistenceException, FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException {
        // Removes an order based off it's date and number associated with it.

        try {
            loadFromFile(order.getOrderDate());
        } catch (FlooringMasteryNoOrderException e) {
            throw new FlooringMasteryNoOrderException("There are no orders matching the input saved in this file.", e);
        } catch (FlooringMasteryInvalidDateException ex) {
            throw new FlooringMasteryInvalidDateException("--- PLEASE ENTER A VALID DATE ---");
        }
        Order removedOrder = orderMap.remove(order.getOrderNumber());
        writeToFile(removedOrder.getOrderDate());
        return removedOrder;
    }

    private void writeToFile(String date)
            throws FlooringMasteryPersistenceException, FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException {
        // Saves a file based off of the day it is created on.

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(createFileName(date)));
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
                    "Unable to save order data", e);
        }
        List<Order> orderList = new ArrayList<>(orderMap.values());
        for (Order o : orderList) {
            out.println(String.valueOf(o.getOrderNumber()) + DELIMITER + o.getCustomerName()
                    + DELIMITER + o.getStateName() + DELIMITER
                    + String.valueOf(o.getTaxRate()) + DELIMITER + o.getProductType() + DELIMITER
                    + String.valueOf(o.getArea()) + DELIMITER + String.valueOf(o.getCostPerSqFt()) + DELIMITER
                    + String.valueOf(o.getLaborCostPerSqFt()) + DELIMITER + String.valueOf(o.getMaterialCost()) + DELIMITER
                    + String.valueOf(o.getLaborCost()) + DELIMITER + String.valueOf(o.getTotalTax())
                    + DELIMITER + String.valueOf(o.getTotalCost()) + DELIMITER + o.getOrderDate());
            out.flush();
        }
        orderMap = new HashMap<>();
        out.close();
    }

    private void loadFromFile(String date)
            throws FlooringMasteryPersistenceException, FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException {
        // Takes in a string format and converts it to a date to retrieve from.
        orderMap = new HashMap<>();
        Scanner sc;

        try {
            sc = new Scanner(new BufferedReader(new FileReader(createFileName(date))));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException("Could not load the order file from memory.", e);
        }

        String currentLine;
        String[] currentToken;

        while (sc.hasNextLine()) {
            currentLine = sc.nextLine();
            currentToken = currentLine.split(DELIMITER);

            int ordernum = Integer.parseInt(currentToken[0]);

            String name = currentToken[1];
            String state = currentToken[2];
            BigDecimal taxR = new BigDecimal(currentToken[3]);
            String productT = currentToken[4];
            BigDecimal a = new BigDecimal(currentToken[5]);
            BigDecimal costPerSq = new BigDecimal(currentToken[6]);
            BigDecimal laborCostPerSq = new BigDecimal(currentToken[7]);
            BigDecimal totalMatC = new BigDecimal(currentToken[8]);
            BigDecimal totalLaborC = new BigDecimal(currentToken[9]);
            BigDecimal taxT = new BigDecimal(currentToken[10]);
            BigDecimal totalCost = new BigDecimal(currentToken[11]);
            date = currentToken[12];

            Order currentOrder = new Order(name, productT, state, a, date);

            currentOrder.setOrderNumber(ordernum);
            currentOrder.setCostPerSqFt(costPerSq);
            currentOrder.setLaborCost(totalLaborC);
            currentOrder.setMaterialCost(totalMatC);
            currentOrder.setTaxRate(taxR);
            currentOrder.setTotalCost(totalCost);
            currentOrder.setTotalTax(taxT);
            currentOrder.setLaborCostPerSqFt(laborCostPerSq);

            orderMap.put(ordernum, currentOrder);
        }
        sc.close();
    }

    private void increment(Order order) throws FlooringMasteryPersistenceException, FlooringMasteryNoOrderException,
            FlooringMasteryInvalidDateException {
        List<Order> orderList;
        orderList = getOrderListByDate(order.getOrderDate());
        for (Order thisOrder : orderList) {
            if (orderList.isEmpty()) {
                break;
            } else if (!orderList.isEmpty()) {
                OptionalInt increment = getOrderListByDate(order.getOrderDate())
                        .stream()
                        .mapToInt((o) -> o.getOrderNumber())
                        .max(); // Lambdas bae-beeeeeeee
                int orderNum = increment.getAsInt() + 1;
                order.setOrderNumber(orderNum);
            }

        }
    }

    private boolean checkIfExists(Order order) throws FlooringMasteryInvalidDateException, FlooringMasteryPersistenceException,
            FlooringMasteryNoOrderException {
        File tempFile = new File(createFileName(order.getOrderDate()));
        return tempFile.exists();
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
