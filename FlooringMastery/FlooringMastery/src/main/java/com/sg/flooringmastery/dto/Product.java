/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author devon
 */
public class Product {
    String productName;
    BigDecimal costPerSqFt;
    BigDecimal labourCostPerSqFt;
    
    public Product(){
        
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getCostPerSqFt() {
        return costPerSqFt;
    }

    public void setCostPerSqFt(BigDecimal costPerSqFt) {
        this.costPerSqFt = costPerSqFt;
    }

    public BigDecimal getLabourCostPerSqFt() {
        return labourCostPerSqFt;
    }

    public void setLabourCostPerSqFt(BigDecimal labourCostPerSqFt) {
        this.labourCostPerSqFt = labourCostPerSqFt;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.productName);
        hash = 67 * hash + Objects.hashCode(this.costPerSqFt);
        hash = 67 * hash + Objects.hashCode(this.labourCostPerSqFt);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (!Objects.equals(this.productName, other.productName)) {
            return false;
        }
        if (!Objects.equals(this.costPerSqFt, other.costPerSqFt)) {
            return false;
        }
        if (!Objects.equals(this.labourCostPerSqFt, other.labourCostPerSqFt)) {
            return false;
        }
        return true;
    }
    
}
