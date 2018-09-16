/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.system.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *product class with required variables and getters and setters
 * changed arraylist to observablelist as allowed by project instructions
 * @author Benjamin
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private IntegerProperty productID;
    private StringProperty name;
    private DoubleProperty price;
    private IntegerProperty inStock;
    private IntegerProperty min;
    private IntegerProperty max;

    public Product(IntegerProperty productID, StringProperty name, DoubleProperty price, 
            IntegerProperty inStock, IntegerProperty min, IntegerProperty max) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
        
    }
    
    

    public int getProductID() {
        return productID.get();
    }

    public void setProductID(int productID) {
        this.productID.set(productID);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public int getInStock() {
        return inStock.get();
    }

    public void setInStock(int inStock) {
        this.inStock.set(inStock);
    }

    public int getMin() {
        return min.get();
    }

    public void setMin(int min) {
        this.min.set(min);
    }

    public int getMax() {
        return max.get();
    }

    public void setMax(int max) {
        this.max.set(max);
    }

    public void addAssociatedPart(Part part)    {
        
       this.associatedParts.add(part);
    }
    
//    skeleton code for required unused method
//     functionality handled in controller classes
    public boolean removeAssociatedPart(int PartID) {
        
        return true;
    }
    
    //    skeleton code for required unused method
//     functionality handled in controller classes
    public Part lookupAssociatedPart(int PartID) {
        
        return this.associatedParts.get(PartID);
    }
    
    public ObservableList<Part> getAssociatedParts() {
        
        return associatedParts;
    }
    
    public IntegerProperty productIDProperty() {
        
        return productID;
    }
    
    public StringProperty productNameProperty() {
        
        return name;
    }
    
    public DoubleProperty productPriceProperty() {
        
        return price;
    }
    
    public IntegerProperty productInvProperty() {
        
        return inStock;
    }
            
    
    
}
            
    

