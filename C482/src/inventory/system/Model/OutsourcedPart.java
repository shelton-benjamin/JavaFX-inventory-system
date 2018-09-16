/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.system.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Benjamin
 * the Outsourced Part class with one variable and getters and setters
 * inherits most properties from Part class
 */
public class OutsourcedPart extends Part {

    private StringProperty companyName;

    public OutsourcedPart(StringProperty companyName, IntegerProperty partID, StringProperty name, DoubleProperty price, IntegerProperty inStock, IntegerProperty min, IntegerProperty max) {
        super(partID, name, price, inStock, min, max);
        this.companyName = companyName;
    }

    public String getCompanyName() {

        return companyName.get();
    }

    public void setCompanyName(String companyName) {

        this.companyName.set(companyName);
    }

    public StringProperty partCompanyNameProperty() {
        return companyName;
    }

}
