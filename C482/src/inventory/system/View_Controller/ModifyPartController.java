/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.system.View_Controller;

import inventory.system.Model.InHousePart;
import inventory.system.Model.Inventory;
import static inventory.system.Model.Inventory.deletePart;
import static inventory.system.Model.Inventory.lookupPart;
import inventory.system.Model.OutsourcedPart;
import inventory.system.Model.Part;
import static inventory.system.View_Controller.MainScreenController.getPartToModify;
import static inventory.system.View_Controller.MainScreenController.getPartToModifyID;
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
public class ModifyPartController implements Initializable {

    private boolean modifyInHouse;

    @FXML
    private RadioButton modifyInHouseRadioButton;
    @FXML
    private RadioButton modifyOutsourcedRadioButton;

    @FXML
    private TextField modifyPartIDField;
    @FXML
    private TextField modifyPartNameField;
    @FXML
    private TextField modifyPartInvField;
    @FXML
    private TextField modifyPartPriceCostField;
    @FXML
    private TextField modifyPartMaxField;
    @FXML
    private TextField modifyPartMinField;

    @FXML
    private Label modifyPartSwitchLabel;
    @FXML
    private TextField modifyPartSwitchTextField;

    @FXML
    private Button modifyPartSaveButton;
    @FXML
    private Button modifyPartCancelButton;

    //inhouse radio button clicked by user
    @FXML
    private void modifyInHouseSelected() throws Exception {

        modifyPartSwitchLabel.setText("Machine ID");
        modifyPartSwitchTextField.setPromptText("Mach ID");
        modifyInHouse = true;
    }

    //outsourced radio button clicked by user
    @FXML
    private void modifyOutsourcedSelected() throws Exception {

        modifyPartSwitchLabel.setText("Company Name");
        modifyPartSwitchTextField.setPromptText("Comp Nm");
        modifyInHouse = false;
    }

    //part is saved with new properties
    @FXML
    private void modifyPart() throws Exception {

        IntegerProperty sameID = new SimpleIntegerProperty(getPartToModifyID());
        StringProperty newName = new SimpleStringProperty((modifyPartNameField.getText()));
        DoubleProperty newPrice = new SimpleDoubleProperty((Double.parseDouble(modifyPartPriceCostField.getText())));
        IntegerProperty newInStock = new SimpleIntegerProperty((Integer.parseInt(modifyPartInvField.getText())));
        IntegerProperty newMin = new SimpleIntegerProperty((Integer.parseInt(modifyPartMinField.getText())));
        IntegerProperty newMax = new SimpleIntegerProperty((Integer.parseInt(modifyPartMaxField.getText())));

        //exception handler so part will not save if min value is more than max value
        if (newMin.get() > newMax.get()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Minimum value cannot be less than maximum value");
            alert.setContentText("Check input and try again.");

            alert.showAndWait();

            //if min value is less than max value, the changes to the part are saved
            //note that the original part is deleted in the inventory list and a new part is saved
            //that has the same ID number
        } else if (modifyInHouse == true) {

            deletePart(getPartToModify());

            IntegerProperty newMachineID = new SimpleIntegerProperty(Integer.parseInt(modifyPartSwitchTextField.getText()));

            InHousePart newPart = new InHousePart(newMachineID, sameID, newName, newPrice, newInStock, newMin, newMax);

            Inventory.addPart(newPart);

            System.out.println("Part Modified");
            System.out.println("Part ID: " + newPart.getPartID());
            System.out.println("Name: " + newPart.getName());
            System.out.println("Type: In-House");
            System.out.println("Inventory: " + newPart.getInStock());
            System.out.println("Price/Cost: " + newPart.getPrice());
            System.out.println("Max: " + newPart.getMax());
            System.out.println("Min: " + newPart.getMin());
            System.out.println("Machine ID: " + newPart.getMachineID());
            System.out.println();

            Stage stage = (Stage) modifyPartSaveButton.getScene().getWindow();
            stage.close();

        } else {

            deletePart(getPartToModify());

            StringProperty newCompanyName = new SimpleStringProperty(modifyPartSwitchTextField.getText());

            OutsourcedPart newPart = new OutsourcedPart(newCompanyName, sameID, newName, newPrice, newInStock, newMin, newMax);

            Inventory.addPart(newPart);

            System.out.println("Part Modified");
            System.out.println("Name: " + newPart.getName());
            System.out.println("Part ID: " + newPart.getPartID());
            System.out.println("Type: Outsourced");
            System.out.println("Inventory: " + newPart.getInStock());
            System.out.println("Price/Cost: " + newPart.getPrice());
            System.out.println("Max: " + newPart.getMax());
            System.out.println("Min: " + newPart.getMin());
            System.out.println("Company Name: " + newPart.getCompanyName());
            System.out.println();

            Stage stage = (Stage) modifyPartSaveButton.getScene().getWindow();
            stage.close();
        }

    }

    //cancel button clicked to close screen
    @FXML
    private void closeModifyPartScreen() throws Exception {
        Stage stage = (Stage) modifyPartCancelButton.getScene().getWindow();
        stage.close();

    }

    /**
     * sets all text fields and radio buttons to reflect properties of
     * the part that's being modified
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Part tempPart = lookupPart(getPartToModifyID());
        modifyPartIDField.setText(String.valueOf(tempPart.getPartID()));
        modifyPartNameField.setText(tempPart.getName());
        modifyPartInvField.setText(String.valueOf(tempPart.getInStock()));
        modifyPartPriceCostField.setText(String.valueOf(tempPart.getPrice()));
        modifyPartMaxField.setText(String.valueOf(tempPart.getMax()));
        modifyPartMinField.setText(String.valueOf(tempPart.getMin()));

        if (tempPart instanceof InHousePart) {

            modifyInHouseRadioButton.fire();
            modifyPartSwitchTextField.setText(String.valueOf(((InHousePart) tempPart).getMachineID()));

        } else {

            modifyOutsourcedRadioButton.fire();
            modifyPartSwitchTextField.setText(String.valueOf(((OutsourcedPart) tempPart).getCompanyName()));

        }
    }
}
