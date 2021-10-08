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
public class Order {

    // When orders are written into a file it will incorporate items from the product and tax DTO's
    /**
     * The file will read like this, OrderNumber, Customer Name, State, Tax
     * Rate, Product Type, Area, Cost per Square Foot, Labor cost per Square
     * Foot, Material Cost, Labor Cost, Tax, Total
     */
    private int orderNumber = 1; // This should increment as orders increase ++
    private String orderDate = "";
    private String customerName = "";
    private String productType = "";
    private String stateName = "";
    private BigDecimal taxRate = new BigDecimal(0); 
// Whole number, tax differs by state
    private BigDecimal area = new BigDecimal(0);
    private BigDecimal costPerSqFt = new BigDecimal(0);
    private BigDecimal materialCost = new BigDecimal(0); 
// Area * costPerSqFt
    private BigDecimal laborCostPerSqFt = new BigDecimal(0);
    private BigDecimal laborCost = new BigDecimal(0); 
// Area * laborCostPerSqFt
    private BigDecimal totalTax = new BigDecimal(0); 
// MaterialCost * laborCost * (taxRate/100)
    private BigDecimal totalCost = new BigDecimal(0); 
// MaterialCost + LaborCost + Tax
    
    public Order(){
        
    }

     public Order(String customerN, String productT, String state, BigDecimal a, String currentDay) {
        this.customerName = customerN;
        this.productType = productT;
        this.stateName = state;
        this.area = a;
        this.orderDate =  currentDay;
    } // The order form that the user will see. Everything else is calculated and checked behind the scenes


    // Increment the order somewhere in here? 
    
    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getCostPerSqFt() {
        return costPerSqFt;
    }

    public void setCostPerSqFt(BigDecimal costPerSqFt) {
        this.costPerSqFt = costPerSqFt;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getLaborCostPerSqFt() {
        return laborCostPerSqFt;
    }

    public void setLaborCostPerSqFt(BigDecimal laborCostPerSqFt) {
        this.laborCostPerSqFt = laborCostPerSqFt;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.orderDate);
        hash = 59 * hash + Objects.hashCode(this.customerName);
        hash = 59 * hash + Objects.hashCode(this.productType);
        hash = 59 * hash + Objects.hashCode(this.stateName);
        hash = 59 * hash + Objects.hashCode(this.taxRate);
        hash = 59 * hash + Objects.hashCode(this.area);
        hash = 59 * hash + Objects.hashCode(this.costPerSqFt);
        hash = 59 * hash + Objects.hashCode(this.materialCost);
        hash = 59 * hash + Objects.hashCode(this.laborCostPerSqFt);
        hash = 59 * hash + Objects.hashCode(this.laborCost);
        hash = 59 * hash + Objects.hashCode(this.totalTax);
        hash = 59 * hash + Objects.hashCode(this.totalCost);
        hash = 59 * hash + this.orderNumber;
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
        final Order other = (Order) obj;
        if (this.orderNumber != other.orderNumber) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.productType, other.productType)) {
            return false;
        }
        if (!Objects.equals(this.stateName, other.stateName)) {
            return false;
        }
        if (!Objects.equals(this.orderDate, other.orderDate)) {
            return false;
        }
        if (!Objects.equals(this.taxRate, other.taxRate)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.costPerSqFt, other.costPerSqFt)) {
            return false;
        }
        if (!Objects.equals(this.materialCost, other.materialCost)) {
            return false;
        }
        if (!Objects.equals(this.laborCostPerSqFt, other.laborCostPerSqFt)) {
            return false;
        }
        if (!Objects.equals(this.laborCost, other.laborCost)) {
            return false;
        }
        if (!Objects.equals(this.totalTax, other.totalTax)) {
            return false;
        }
        if (!Objects.equals(this.totalCost, other.totalCost)) {
            return false;
        }
        return true;
    }
}
