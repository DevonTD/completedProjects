/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import static com.sg.flooringmastery.dao.OrderDaoImpl.DELIMITER;
import com.sg.flooringmastery.dto.Order;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author devon
 */
public class ExportDataDaoImpl implements ExportDao {

    OrderDao dao = new OrderDaoImpl();

//    public ExportDataDaoImpl(OrderDao dao) {
//        this.dao = dao;
//    }

    public ExportDataDaoImpl() {

    }
    
    @Override
    public void writeFile(List<String> myList) throws FileNotFoundException, FlooringMasteryPersistenceException {
        try {
            PrintWriter pw;
            
            try {
                pw = new PrintWriter(new FileWriter("ExportData.txt"));
            } catch (IOException ex) {
                throw new FlooringMasteryPersistenceException("Could not save data.", ex);
            }
            List<Order> orderList;
            for (int i = 0; i < myList.size(); i++) {
                orderList = dao.getOrderListByDate(myList.get(i)); 
                for (Order o : orderList) {
                    pw.println(String.valueOf(o.getOrderNumber()) + DELIMITER + o.getCustomerName()
                            + DELIMITER + o.getStateName() + DELIMITER
                            + String.valueOf(o.getTaxRate()) + DELIMITER + o.getProductType() + DELIMITER
                            + String.valueOf(o.getArea()) + DELIMITER + String.valueOf(o.getCostPerSqFt()) + DELIMITER
                            + String.valueOf(o.getLaborCostPerSqFt()) + DELIMITER + String.valueOf(o.getMaterialCost()) + DELIMITER
                            + String.valueOf(o.getLaborCost()) + DELIMITER + String.valueOf(o.getTotalTax())
                            + DELIMITER + String.valueOf(o.getTotalCost()) + DELIMITER + o.getOrderDate());
                    pw.flush();
                }
            }
            pw.close();
        } catch (FlooringMasteryNoOrderException | FlooringMasteryInvalidDateException ex) {
            Logger.getLogger(ExportDataDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
