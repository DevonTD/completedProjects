/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author devon
 */
public class ProductDaoImpl implements ProductDao {

    private final String PRODUCT_FILE;
    public static final String DELIMITER = ",";

    private final HashMap<String, Product> product;

    public ProductDaoImpl() {
        this.product = new HashMap<>();
        this.PRODUCT_FILE = "Products.txt";
    }

    public ProductDaoImpl(String productFile) {
        this.product = new HashMap<>();
        this.PRODUCT_FILE = productFile;
    }

    @Override
    public Product getProduct(String key) 
            throws FlooringMasteryPersistenceException{
        loadFile();
        Product p = product.get(key);
        return p;
    }

    @Override
    public List<Product> displayProduct() 
            throws FlooringMasteryPersistenceException {
        loadFile();
        return new ArrayList<>(product.values());
    }


    private void loadFile() 
            throws FlooringMasteryPersistenceException {
        Scanner scan;

        try {
            scan = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException("Could not load the product file from memory.");
        }
        String currentLine;
        String[] currentToken;

        while (scan.hasNextLine()) {
            currentLine = scan.nextLine();
            currentToken = currentLine.split(DELIMITER);

            Product newProduct = new Product();
            
            newProduct.setProductName(currentToken[0]);
            newProduct.setCostPerSqFt(new BigDecimal(currentToken[1]));
            newProduct.setLabourCostPerSqFt(new BigDecimal(currentToken[2]));
       
            product.put(newProduct.getProductName(), newProduct);
        }
       scan.close();
    }
}
