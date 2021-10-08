/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author devon
 */
public class FlooringMasteryView {

    UserIO io;

    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }

    public int printMenu() {
        io.print("**********************************");
        io.print("*<< FLOORING PROGRAM >>");
        io.print("*1. Display Orders");
        io.print("*2. Add an Order");
        io.print("*3. Edit an Order");
        io.print("*4. Remove an Order");
        io.print("*5. Export All Data");
        io.print("*6. Quit");
        return io.readInt("**********************************", 1, 6);
    }

    public Order newOrderInfo() {
        Order order = new Order();
        String customerName = io.readString("Please enter a name, allowed characters are [a-z] [0-9] as well as periods and comma's. ");
        order.setCustomerName(customerName);
        String stateName = io.readString("Enter your states name here, abbreviations are accepted.");
        order.setStateName(stateName);
        String productType = io.readString("Enter the type of product you're looking to purchase");
        order.setProductType(productType);
        BigDecimal area = io.readDecimal("Enter the area, minimum order size is 100 Sq Ft", new BigDecimal("100"));
        order.setArea(area);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate ld = LocalDate.now();
        Boolean run = true;
        while(run){
            String dayDate = io.readString("Enter the date you'd like the order to be placed.");
            LocalDate userInput = LocalDate.parse(dayDate, formatter);
            if(userInput.isBefore(ld)){
                io.print("Please enter a valid date");
            }else{
                order.setOrderDate(dayDate);
                run = false;
            }
        }
        return order;
    }

    public String listOfOrdersByDatePrompt() {
        return io.readString("Please enter the date of the orders you'd like to view/select");
    }

    public void listOfOrdersByDate(List<Order> orderList) {
        for (Order order : orderList) {
            io.print(String.valueOf(order.getOrderNumber()) + " , " + order.getCustomerName() + " , "
                    + order.getStateName() + " , " + order.getProductType() + " , " + order.getOrderDate());

        }
        io.print("\n");
    }

    public void genericText(String msg) {
        io.print(msg);
    }

    public int genericTextInt() {
        return io.readInt("Please enter the order number for the specific order you wish to select");
    }

    public int updateInfoPrompt() {
        io.print("=== EDIT ORDER INFORMATION ===");
        io.print("1. Customer Name");
        io.print("2. State");
        io.print("3. Product Type");
        io.print("4. Area");
        io.print("5. Return to main menu.\n");
        return io.readInt("Please choose from one of the available options you'd like to make changes to.", 1, 5);
    }

    public String changeInfoGeneric() {
        return io.readString("Enter changes here.");
    }

    public BigDecimal changeInfoGenericBigDecimal() {
        return io.readDecimal("Enter changes here");
    }

    public void removeOrderBanner() {
        io.print("===== REMOVE ORDER MENU =====");
    }

    public void orderListBanner() {
        io.print("===== ORDER LIST MENU =====");
    }

    public void addOrderBanner() {
        io.print("===== ADD ORDER MENU =====");
    }

    public void updateOrderBanner() {
        io.print("===== UPDATE ORDER MENU =====");
    }

    public void genericSuccessBanner() {
        io.print(".");
        io.print(".");
        io.print(".");
        io.print("... Success!");
    }

    public String removeOrderGenericString() {
        return io.readString("Enter the date of the order you wish to remove.");
    }

    public int removeOrderGenericInt() {
        return io.readInt("Enter the number associated with the order you wish to remove.");
    }

    public void viewOrder(Order order) {
        io.print(String.valueOf(order.getOrderNumber()) + " - " + order.getCustomerName() + " - "
                + order.getStateName() + " - " + order.getProductType() + " - " + order.getOrderDate());
    }

    public int toRemoveOrNotToRemove() {
        io.print("Is this the order you wish to remove? Selecting the options below..");
        io.print("1. YES");
        io.print("2. NO");
        return io.readInt("Please select the following options", 1, 2);
    }

    public void genericRemovalSuccessBanner() {
        io.print(".");
        io.print(".");
        io.print(".");
        io.print("... Success! The selected order has been expunged.");
    }

    public void orderRemovalWithheld() {
        io.print("The selected Order has not been removed.");
        io.print("Returning to main menu..");
        io.print("\n");
    }

    public void genericErrorMsg() {
        io.print("Hmmm.. Something went wrong.");
    }

    public int export() {
        io.print("Export existing data? Selecting the options below.");
        io.print("1. YES");
        io.print("2. NO");
        return io.readInt("Please select the following options", 1, 2);
    }

    public void exportSuccess() {
        io.print(".");
        io.print(".");
        io.print(".");
        io.print("... Success!");
    }

    public void exportBanner() {
        io.print("===== EXPORT DATA =====");
    }

    public void exportDenied() {
        io.print("Export withheld.. Returning to menu!");
    }
}
