/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.FlooringMasteryInvalidAreaInputException;
import com.sg.flooringmastery.dao.FlooringMasteryInvalidCustomerNameException;
import com.sg.flooringmastery.dao.FlooringMasteryInvalidDateException;
import com.sg.flooringmastery.dao.FlooringMasteryInvalidProductNameException;
import com.sg.flooringmastery.dao.FlooringMasteryInvalidStateNameException;
import com.sg.flooringmastery.dao.FlooringMasteryNoOrderException;
import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.service.FlooringMasteryService;
import com.sg.flooringmastery.ui.FlooringMasteryView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author devon
 */
public class FlooringMasteryController {

    FlooringMasteryView view;
    FlooringMasteryService service;

    public FlooringMasteryController(FlooringMasteryView view, FlooringMasteryService service) {
        this.view = view;
        this.service = service;
    }

    public void run() throws FlooringMasteryPersistenceException, FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException,
            FlooringMasteryInvalidAreaInputException, FlooringMasteryInvalidStateNameException, FlooringMasteryInvalidStateNameException,
            FlooringMasteryInvalidCustomerNameException, FlooringMasteryInvalidProductNameException, IOException {
        boolean keepGoing = true;
        int menuSelect = 0;
        while (keepGoing) {

            menuSelect = menu();
            try{
                
            
            switch (menuSelect) {
                case 1:
                    orderList(); 
                    break;
                case 2:
                    addOrder(); 
                    break;
                case 3:
                    updateOrder(); // Needs to be looked at [returning null values]
                    break;
                case 4:
                    removeOrder(); 
                    break;
                case 5:
                    exportOrder();
                    break;
                case 6:
                    keepGoing = false;
                    break;
                default:
                    view.genericText("Input one of the above choices, please.");
            }
            }catch(Exception exception){
                System.out.println("Unexpected error has happened. Returning to main menu.");
               // exception.printStackTrace();
            }
            
        }
    }

    private int menu() {
        return view.printMenu();
    }

    private void addOrder() throws FlooringMasteryPersistenceException, FlooringMasteryNoOrderException, IOException, 
            FlooringMasteryInvalidDateException, FlooringMasteryInvalidAreaInputException, FlooringMasteryInvalidStateNameException, 
            FlooringMasteryInvalidCustomerNameException, FlooringMasteryInvalidProductNameException  {
        view.addOrderBanner();
        Order newOrder = view.newOrderInfo();
        service.addOrder(newOrder);
        view.genericSuccessBanner();
    }

    private void orderList() throws FlooringMasteryNoOrderException, FlooringMasteryPersistenceException, FlooringMasteryInvalidDateException {
        view.orderListBanner();
        String orderDate = view.listOfOrdersByDatePrompt();
        List<Order> myList = service.displayOrdersByDate(orderDate);
        view.genericSuccessBanner();
        view.listOfOrdersByDate(myList);
    }

    private void updateOrder() throws FlooringMasteryPersistenceException, FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException,
            FlooringMasteryInvalidAreaInputException, FlooringMasteryInvalidStateNameException, FlooringMasteryInvalidCustomerNameException,
            FlooringMasteryInvalidProductNameException, IOException {
        view.updateOrderBanner();
        String orderDate = view.listOfOrdersByDatePrompt();
        int orderNumber = view.genericTextInt();
        Order getOrder = service.getOrderByDate(orderDate, orderNumber);
        Order updatedOrder = new Order();
        boolean run = true;
        int updateInfo = 0;
        while (run) {
            updateInfo = view.updateInfoPrompt();
            switch (updateInfo) {
                case 1:
                    String newName = view.changeInfoGeneric();
                    updatedOrder.setCustomerName(newName);
                    view.genericSuccessBanner();
                    break;
                case 2:
                    String newState = view.changeInfoGeneric();
                    updatedOrder.setStateName(newState);
                    view.genericSuccessBanner();
                    break;
                case 3:
                    String newProduct = view.changeInfoGeneric();
                    updatedOrder.setProductType(newProduct);
                    view.genericSuccessBanner();
                    break;
                case 4:
                    BigDecimal newArea = view.changeInfoGenericBigDecimal();
                    updatedOrder.setArea(newArea);
                    view.genericSuccessBanner();
                    break;
                case 5:
                    run = false;
                    break;
            }
        }
        service.updateOrder(getOrder, updatedOrder);
    }

    private void removeOrder() throws FlooringMasteryPersistenceException, FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException {
        view.removeOrderBanner();
        String orderDate = view.removeOrderGenericString();
        int orderNumber = view.removeOrderGenericInt();
        Order getOrder = service.getOrderByDate(orderDate, orderNumber);
        view.viewOrder(getOrder);
        boolean run = true;
        int trueOrFalse = 0;
        while(run){
            trueOrFalse = view.toRemoveOrNotToRemove();
            switch(trueOrFalse){
                case 1:
                    service.removeOrder(getOrder);
                    view.genericRemovalSuccessBanner();
                    run = false;
                    break;
                case 2:
                    view.orderRemovalWithheld();
                    run = false;
                    break;
                default:
                    view.genericErrorMsg();
            }
        }
    }
    private void exportOrder() throws FileNotFoundException, FlooringMasteryPersistenceException, 
            FlooringMasteryNoOrderException, FlooringMasteryInvalidDateException{
        view.exportBanner();
         boolean run = true;
        int trueOrFalse = 0;
        while(run){
            trueOrFalse = view.export();
                  switch(trueOrFalse){
                case 1:
                    List<String> myList = service.getFileNames();
                    service.exportData(myList);
                    view.exportSuccess();
                    run = false;
                    break;
                case 2:
                    view.exportDenied();
                    run = false;
                    break;
                default:
                    view.genericErrorMsg();
            }
        }
    }
}
