/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.system.View_Controller;

import inventory.system.Model.Inventory;
import static inventory.system.Model.Inventory.addProduct;
import static inventory.system.Model.Inventory.getListOfParts;
import static inventory.system.Model.Inventory.increaseProductCount;
import inventory.system.Model.Part;
import inventory.system.Model.Product;
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
import javafx.scene.control.Alert.AlertType;
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
public class AddProductController implements Initializable {

    //observable list used when creating a new part
    private ObservableList<Part> thisProductParts = FXCollections.observableArrayList();

    @FXML
    private TextField addProductNameField;
    @FXML
    private TextField addProductInvField;
    @FXML
    private TextField addProductPriceField;
    @FXML
    private TextField addProductMaxField;
    @FXML
    private TextField addProductMinField;

    @FXML
    private Button addProductSearchButton;
    @FXML
    private TextField addProductSearchText;
    @FXML
    private Button addProductAddPartButton;

    @FXML
    private TableView addProductSearchPartsTable;
    @FXML
    private TableColumn<Part, Integer> addProductSearchPartsTablePartIDColumn;
    @FXML
    private TableColumn<Part, String> addProductSearchPartsTablePartNameColumn;
    @FXML
    private TableColumn<Part, Integer> addProductSearchPartsTablePartInvColumn;
    @FXML
    private TableColumn<Part, Double> addProductSearchPartsTablePartPriceColumn;

    @FXML
    private TableView addProductAssociatedPartsTable;
    @FXML
    private TableColumn<Part, Integer> addProductAssociatedPartsTableIDColumn;
    @FXML
    private TableColumn<Part, String> addProductAssociatedPartsTableNameColumn;
    @FXML
    private TableColumn<Part, Integer> addProductAssociatedPartsTableInvColumn;
    @FXML
    private TableColumn<Part, Double> addProductAssociatedPartsTablePriceColumn;

    @FXML
    private Button addProductDeletePartButton;
    @FXML
    private Button addProductSavePartButton;

    @FXML
    private Button addProductCancelButton;

    //save button clicked to create a new product
    @FXML
    private void createProduct() throws Exception {

        IntegerProperty newID = new SimpleIntegerProperty(Inventory.getProductCount());
        StringProperty newName = new SimpleStringProperty((addProductNameField.getText()));
        DoubleProperty newPrice = new SimpleDoubleProperty((Double.parseDouble(addProductPriceField.getText())));
        IntegerProperty newInStock = new SimpleIntegerProperty((Integer.parseInt(addProductInvField.getText())));
        IntegerProperty newMin = new SimpleIntegerProperty((Integer.parseInt(addProductMinField.getText())));
        IntegerProperty newMax = new SimpleIntegerProperty((Integer.parseInt(addProductMaxField.getText())));

        int partsPriceSum = 0;
        for (Part part : thisProductParts) {

            partsPriceSum += part.getPrice();
        }

        //exception handler to ensure min value is less than max value
        if (newMin.get() > newMax.get()) {

            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Minimum value cannot be less than maximum value");
            alert.setContentText("Check input and try again.");

            alert.showAndWait();

            //exception handler to ensure price of all the parts does not exceed price of product
        } else if (newPrice.get() < partsPriceSum) {

            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Product price cannot be less than sum of price of parts.");
            alert.setContentText("Check input and try again.");

            alert.showAndWait();

            //if above conditions are met, a new product is created and added to inventory
        } else {

            Product newProduct = new Product(newID, newName, newPrice, newInStock, newMin, newMax);

            for (Part part : thisProductParts) {

                newProduct.addAssociatedPart(part);
            }

            addProduct(newProduct);
            increaseProductCount();

            System.out.println("New Product Added");
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
            Stage stage = (Stage) addProductSavePartButton.getScene().getWindow();
            stage.close();

        }
    }

    //searches for a part in the inventory by name or partID
    @FXML
    private void addProductSearchParts() throws Exception {

        ObservableList<Part> tempParts = FXCollections.observableArrayList();
        ObservableList<Part> myList = getListOfParts();

        String searchTerm = addProductSearchText.getText();

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

        addProductSearchPartsTable.setItems(tempParts);

    }

    //adds the selected part from search results to new product
    @FXML
    private void addProductAddPartToProduct() throws Exception {
        try {
            Part part = (Part) addProductSearchPartsTable.getSelectionModel().getSelectedItem();
            thisProductParts.add(part);

            addProductAssociatedPartsTable.setItems(thisProductParts);
        } catch (NullPointerException ex) {

        }
    }

    //removes selected part from new product
    @FXML
    private void addProductDeletePart() throws Exception {
        try {
            Part part = (Part) addProductAssociatedPartsTable.getSelectionModel().getSelectedItem();
            int myIndex = thisProductParts.indexOf(part);
            thisProductParts.remove(myIndex);

            addProductAssociatedPartsTable.setItems(thisProductParts);
        } catch (NullPointerException ex) {

        }

    }

    //cancel button clicked to close screen
    @FXML
    private void closeAddProductScreen() throws Exception {
        Stage stage = (Stage) addProductCancelButton.getScene().getWindow();
        stage.close();

    }

    /**
     * sets the tableviews to show part search results and parts associated with new product
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addProductSearchPartsTablePartIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        addProductSearchPartsTablePartNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        addProductSearchPartsTablePartInvColumn.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
        addProductSearchPartsTablePartPriceColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());

        addProductAssociatedPartsTableIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        addProductAssociatedPartsTableNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        addProductAssociatedPartsTableInvColumn.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
        addProductAssociatedPartsTablePriceColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());

    }

}
