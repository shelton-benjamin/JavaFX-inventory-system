/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.system.View_Controller;

import inventory.system.Model.*;
import static inventory.system.Model.Inventory.increasePartCount;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Benjamin
 */
public class AddPartController implements Initializable {

    //boolean to determine if new part will be inhouse or outsourced
    private boolean addInHouse = true;

    @FXML
    private RadioButton addInHouseRadioButton;
    @FXML
    private RadioButton addOutsourcedRadioButton;

    @FXML
    private TextField addPartNameField;
    @FXML
    private TextField addPartInvField;
    @FXML
    private TextField addPartPriceCostField;
    @FXML
    private TextField addPartMaxField;
    @FXML
    private TextField addPartMinField;

    @FXML
    private Label addPartSwitchLabel;
    @FXML
    private TextField addPartSwitchTextField;

    @FXML
    private Button addPartSaveButton;
    @FXML
    private Button addPartCancelButton;

    //inhouse radio button is clicked by user
    //selected by default when opening new add part screen
    @FXML
    private void addInHouseSelected() throws Exception {

        addPartSwitchLabel.setText("Machine ID");
        addPartSwitchTextField.setPromptText("Mach ID");
        addInHouse = true;
    }

    //outsourced radio button is clicked by user
    @FXML
    private void addOutsourcedSelected() throws Exception {

        addPartSwitchLabel.setText("Company Name");
        addPartSwitchTextField.setPromptText("Comp Nm");
        addInHouse = false;
    }

    //click the save button to make a new part
    @FXML
    private void createPart() throws Exception {

        IntegerProperty newID = new SimpleIntegerProperty(Inventory.getPartCount());
        StringProperty newName = new SimpleStringProperty((addPartNameField.getText()));
        DoubleProperty newPrice = new SimpleDoubleProperty((Double.parseDouble(addPartPriceCostField.getText())));
        IntegerProperty newInStock = new SimpleIntegerProperty((Integer.parseInt(addPartInvField.getText())));
        IntegerProperty newMin = new SimpleIntegerProperty((Integer.parseInt(addPartMinField.getText())));
        IntegerProperty newMax = new SimpleIntegerProperty((Integer.parseInt(addPartMaxField.getText())));

        //exception handler to ensure min value is less than max value
        if (newMin.get() > newMax.get()) {

            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Minimum value cannot be less than maximum value");
            alert.setContentText("Check input and try again.");

            alert.showAndWait();

            //if min value is less than max value,
            //a new inhouse or outsourced part will be created and added to inventory
        } else if (addInHouse == true) {

            IntegerProperty newMachineID = new SimpleIntegerProperty(Integer.parseInt(addPartSwitchTextField.getText()));

            InHousePart newPart = new InHousePart(newMachineID, newID, newName, newPrice, newInStock, newMin, newMax);

            Inventory.addPart(newPart);

            System.out.println("Part Added");
            System.out.println("Part ID: " + newPart.getPartID());
            System.out.println("Name: " + newPart.getName());
            System.out.println("Type: In-House");
            System.out.println("Inventory: " + newPart.getInStock());
            System.out.println("Price/Cost: " + newPart.getPrice());
            System.out.println("Max: " + newPart.getMax());
            System.out.println("Min: " + newPart.getMin());
            System.out.println("Machine ID: " + newPart.getMachineID());
            System.out.println();

            increasePartCount();
            Stage stage = (Stage) addPartSaveButton.getScene().getWindow();
            stage.close();

        } else {

            StringProperty newCompanyName = new SimpleStringProperty(addPartSwitchTextField.getText());

            OutsourcedPart newPart = new OutsourcedPart(newCompanyName, newID, newName, newPrice, newInStock, newMin, newMax);

            Inventory.addPart(newPart);

            System.out.println("Part Added");
            System.out.println("Name: " + newPart.getName());
            System.out.println("Part ID: " + newPart.getPartID());
            System.out.println("Type: Outsourced");
            System.out.println("Inventory: " + newPart.getInStock());
            System.out.println("Price/Cost: " + newPart.getPrice());
            System.out.println("Max: " + newPart.getMax());
            System.out.println("Min: " + newPart.getMin());
            System.out.println("Company Name: " + newPart.getCompanyName());
            System.out.println();

            increasePartCount();
            Stage stage = (Stage) addPartSaveButton.getScene().getWindow();
            stage.close();

        }

    }

    //cancel button clicked to close screen
    @FXML
    private void closeAddPartScreen() throws Exception {
        Stage stage = (Stage) addPartCancelButton.getScene().getWindow();
        stage.close();

    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
