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
 * the inHouse Part class with one variable and getters and setters
 * inherits most properties from Part class
 */
public class InHousePart extends Part {

    private IntegerProperty machineID;

    public InHousePart(IntegerProperty machineID, IntegerProperty partID, StringProperty name, DoubleProperty price, IntegerProperty inStock, IntegerProperty min, IntegerProperty max) {
        super(partID, name, price, inStock, min, max);
        this.machineID = machineID;
    }

    public int getMachineID() {

        return machineID.get();
    }

    public void setMachineID(int machineID) {

        this.machineID.set(machineID);
    }

    public IntegerProperty partMachineIDProperty() {
        return machineID;
    }

}
