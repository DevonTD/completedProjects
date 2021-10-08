/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author devon
 */
public class ProductDaoImplTest {

    ProductDao testDao;

    public ProductDaoImplTest() {
    }

    @BeforeEach
    public void setUp() {
        String testFile = "testProduct.txt";
        testDao = new ProductDaoImpl(testFile);
    }

    @Test
    public void testGetProduct()
            throws FlooringMasteryPersistenceException {
        Product currentProduct = testDao.getProduct("Carpet");
        
        assertEquals("Carpet", currentProduct.getProductName(), "Should be Carpet");
        assertEquals(new BigDecimal("2.25"), currentProduct.getCostPerSqFt(), "Should be 2.25");
        assertEquals(new BigDecimal("2.10"), currentProduct.getLabourCostPerSqFt(), "Should be 2.10");
    }

    @Test
    public void testGetProductList()
            throws FlooringMasteryPersistenceException {
        List<Product> availableItems = testDao.displayProduct();
        assertNotNull(availableItems, "List should not be null");
        assertEquals(2, availableItems.size(), "Should equal 2");
    }
}


    