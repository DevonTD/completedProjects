/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author devon
 */
public class TaxDaoImplTest {
    TaxDao testDao;
    
    public TaxDaoImplTest() {
    }
    
    @BeforeEach
    public void setUp() throws IOException{
        String testFile = "testTax.txt";
        testDao = new TaxDaoImpl(testFile);
    }
    

    @Test
    public void testGetTaxInfo() throws FlooringMasteryPersistenceException {
        String stateName = "MN";
        Tax t = testDao.getTax(stateName);
        assertEquals("MN", t.getStateAb(), "Return should be MN");
        assertEquals(new BigDecimal("4.25"), t.getTaxRate(),"Tax rate should be 4.25");
        
        String state = "TX";
        Tax tax = testDao.getTax(state);
        assertEquals("TX", tax.getStateAb(), "Return should be TX");
        assertEquals(new BigDecimal("3.45"),tax.getTaxRate(), "Tax rate should be 3.45");
    }
    
    @Test
    public void testDisplayTax() throws FlooringMasteryPersistenceException {
        List<Tax> taxList = testDao.displayTax();
        assertNotNull(taxList, "Should not be null");
        assertEquals(2, taxList.size(),"List size should be 2");
    }
}
