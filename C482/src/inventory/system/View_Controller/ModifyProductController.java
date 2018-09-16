/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.system.View_Controller;

import static inventory.system.Model.Inventory.addProduct;
import static inventory.system.Model.Inventory.getListOfParts;
import static inventory.system.Model.Inventory.lookupProduct;
import static inventory.system.Model.Inventory.removeProduct;
import inventory.system.Model.Part;
import inventory.system.Model.Product;
import static inventory.system.View_Controller.MainScreenController.getProductToModifyID;
import static inventory.system.View_Controller.MainScreenController.isSearchInteger;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Benjamin
 */
public class ModifyProductController implements Initializable {

    //observable list that holds parts associated with part being modified
    private ObservableList<Part> thisProductParts = FXCollections.observableArrayList();

    @FXML
    private TextField modifyProductIDField;
    @FXML
    private TextField modifyProductNameField;
    @FXML
    private TextField modifyProductInvField;
    @FXML
    private TextField modifyProductPriceField;
    @FXML
    private TextField modifyProductMaxField;
    @FXML
    private TextField modifyProductMinField;

    @FXML
    private Button modifyProductSearchButton;
    @FXML
    private TextField modifyProductSearchText;
    @FXML
    private Button modifyProductAddPartButton;

    @FXML
    private TableView modifyProductSearchPartsTable;
    @FXML
    private TableColumn<Part, Integer> modifyProductSearchPartsTablePartIDColumn;
    @FXML
    private TableColumn<Part, String> modifyProductSearchPartsTablePartNameColumn;
    @FXML
    private TableColumn<Part, Integer> modifyProductSearchPartsTablePartInvColumn;
    @FXML
    private TableColumn<Part, Double> modifyProductSearchPartsTablePartPriceColumn;

    @FXML
    private TableView modifyProductAssociatedPartsTable;
    @FXML
    private TableColumn<Part, Integer> modifyProductAssociatedPartsTableIDColumn;
    @FXML
    private TableColumn<Part, String> modifyProductAssociatedPartsTableNameColumn;
    @FXML
    private TableColumn<Part, Integer> modifyProductAssociatedPartsTableInvColumn;
    @FXML
    private TableColumn<Part, Double> modifyProductAssociatedPartsTablePriceColumn;

    @FXML
    private Button modifyProductDeletePartButton;
    @FXML
    private Button modifyProductSavePartButton;

    @FXML
    private Button modifyProductCancelButton;

    
    //save clicked to modify product
    @FXML
    private void modifyProduct() throws Exception {

        IntegerProperty sameID = new SimpleIntegerProperty(getProductToModifyID());
        StringProperty newName = new SimpleStringProperty((modifyProductNameField.getText()));
        DoubleProperty newPrice = new SimpleDoubleProperty((Double.parseDouble(modifyProductPriceField.getText())));
        IntegerProperty newInStock = new SimpleIntegerProperty((Integer.parseInt(modifyProductInvField.getText())));
        IntegerProperty newMin = new SimpleIntegerProperty((Integer.parseInt(modifyProductMinField.getText())));
        IntegerProperty newMax = new SimpleIntegerProperty((Integer.parseInt(modifyProductMaxField.getText())));

        int partsPriceSum = 0;
        for (Part part : thisProductParts) {

            partsPriceSum += part.getPrice();
        }

        //exception handler to ensure min value is less than max value
        if (newMin.get() > newMax.get()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Minimum value cannot be less than maximum value");
            alert.setContentText("Check input and try again.");

            alert.showAndWait();

            //exception handler to ensure price of all the parts does not exceed price of product
        } else if (newPrice.get() < partsPriceSum) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Product price cannot be less than sum of price of parts.");
            alert.setContentText("Check input and try again.");

            alert.showAndWait();

            //if above conditions are met, the changes to the product are saved
            //note that this deletes the original product from inventory and adds a new one
            //new product will maintain same product ID
        } else {

            removeProduct(getProductToModifyID());

            Product newProduct = new Product(sameID, newName, newPrice, newInStock, newMin, newMax);

            for (Part part : thisProductParts) {

                newProduct.addAssociatedPart(part);
            }

            addProduct(newProduct);

            System.out.println("Product Modified");
            System.out.println("Product ID: " + newProduct.getProductID());
            System.out.println("Name: " + newProduct.getName());
            System.out.println("Price: " + newProduct.getPrice());
            System.out.println("Inventory: " + newProduct.getInStock());
            System.out.println("Min: " + newProduct.getMin());
            System.out.println("Max: " + newProduct.getMax());
            System.out.println("Parts in this product");

            for (Part part : newProduct.getAssociatedParts()) {

                System.out.println("Part ID: " + part.getPartID());
                System.out.println("Part Name: " + part.getName());

            }

            System.out.println();
            Stage stage = (Stage) modifyProductSavePartButton.getScene().getWindow();
            stage.close();

        }
    }

    //searches parts inventory for parts to add to product
    @FXML
    private void modifyProductSearchParts() throws Exception {

        ObservableList<Part> tempParts = FXCollections.observableArrayList();
        ObservableList<Part> myList = getListOfParts();

        String searchTerm = modifyProductSearchText.getText();

        if (isSearchInteger(searchTerm) == true) {

            for (Part part : myList) {
                if (Integer.parseInt(searchTerm) == (part.getPartID())
                        || searchTerm.toUpperCase().trim().equals(part.getName().toUpperCase().trim())) {
                    tempParts.add(part);
                }
            }

        } else {

            for (Part part : myList) {
                if (searchTerm.toUpperCase().trim().equals(part.getName().toUpperCase().trim())) {
                    tempParts.add(part);
                }
            }
        }

        modifyProductSearchPartsTable.setItems(tempParts);

    }

    //adds selected part to the product
    @FXML
    private void modifyProductAddPartToProduct() throws Exception {
        try {
            Part part = (Part) modifyProductSearchPartsTable.getSelectionModel().getSelectedItem();
            thisProductParts.add(part);

            modifyProductAssociatedPartsTable.setItems(thisProductParts);
        } catch (NullPointerException ex) {

        }
    }

//    removes selected part from the product
    @FXML
    private void modifyProductDeletePart() throws Exception {
        try {
            Part part = (Part) modifyProductAssociatedPartsTable.getSelectionModel().getSelectedItem();
            int myIndex = thisProductParts.indexOf(part);
            thisProductParts.remove(myIndex);

            modifyProductAssociatedPartsTable.setItems(thisProductParts);
        } catch (NullPointerException ex) {

        }

    }

    //cancel button clicked to close screen
    @FXML
    private void closeModifyProductScreen() throws Exception {
        Stage stage = (Stage) modifyProductCancelButton.getScene().getWindow();
        stage.close();

    }

    private void updateAssociatedPartsTable() {

        modifyProductAssociatedPartsTable.setItems(thisProductParts);
    }

    private void fillThisProductParts() {

        Product tempProduct = lookupProduct(getProductToModifyID());
        for (Part part : tempProduct.getAssociatedParts()) {

            thisProductParts.add(part);
        }
    }

    /**
     * sets the tableviews to show search results of parts in inventory 
     * and parts currently associated with part being modified
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Product tempProduct = lookupProduct(getProductToModifyID());
        modifyProductIDField.setText(String.valueOf(tempProduct.getProductID()));
        modifyProductNameField.setText(tempProduct.getName());
        modifyProductInvField.setText(String.valueOf(tempProduct.getInStock()));
        modifyProductPriceField.setText(String.valueOf(tempProduct.getPrice()));
        modifyProductMaxField.setText(String.valueOf(tempProduct.getMax()));
        modifyProductMinField.setText(String.valueOf(tempProduct.getMin()));

        modifyProductSearchPartsTablePartIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        modifyProductSearchPartsTablePartNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        modifyProductSearchPartsTablePartInvColumn.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
        modifyProductSearchPartsTablePartPriceColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());

        modifyProductAssociatedPartsTableIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        modifyProductAssociatedPartsTableNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        modifyProductAssociatedPartsTableInvColumn.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
        modifyProductAssociatedPartsTablePriceColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
        fillThisProductParts();
        updateAssociatedPartsTable();
    }

}
