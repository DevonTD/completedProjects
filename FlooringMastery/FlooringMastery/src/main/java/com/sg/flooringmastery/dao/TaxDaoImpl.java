/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;
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
public class TaxDaoImpl implements TaxDao {

    private final String TAX_FILE;
    public static final String DELIMITER = ",";

    private final HashMap<String, Tax> tax;

    public TaxDaoImpl() {
        this.tax = new HashMap<>();
        this.TAX_FILE = "Taxes.txt";
    }

    public TaxDaoImpl(String taxFile) {
        this.tax = new HashMap<>();
        this.TAX_FILE = taxFile;
    }

    @Override
    public Tax getTax(String key)
            throws FlooringMasteryPersistenceException {
        loadFile();
        Tax t = tax.get(key);
        return t;
    }

    @Override
    public List<Tax> displayTax() 
            throws FlooringMasteryPersistenceException {
        loadFile();
       return new ArrayList<>(tax.values());
    }

    public void loadFile()
            throws FlooringMasteryPersistenceException {
        Scanner sc;

        try {
            sc = new Scanner(new BufferedReader(new FileReader(TAX_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException("Could not load the tax file from memory.", e);
        }

        String currentLine;
        String[] currentToken;

        while (sc.hasNextLine()) {
            currentLine = sc.nextLine();
            currentToken = currentLine.split(DELIMITER);

            Tax newTax = new Tax();

            newTax.setStateAb(currentToken[0]);
            newTax.setTaxRate(new BigDecimal(currentToken[1]));

            tax.put(newTax.getStateAb(), newTax);
        }
        sc.close();
    }
}
