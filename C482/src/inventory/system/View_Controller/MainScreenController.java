/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.system.View_Controller;

import static inventory.system.Model.Inventory.deletePart;
import static inventory.system.Model.Inventory.getListOfParts;
import static inventory.system.Model.Inventory.getListOfProducts;
import static inventory.system.Model.Inventory.removeProduct;
import inventory.system.Model.Part;
import inventory.system.Model.Product;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Benjamin
 */
public class MainScreenController implements Initializable {

//    variables that assist with modify part and product controllers
    private static Part partToModify;
    private static int partToModifyID;

    private static Product productToModify;
    private static int productToModifyID;

    @FXML
    private AnchorPane mainScreen;

    @FXML
    private TableView<Part> mainTableViewPart;
    @FXML
    private TableColumn<Part, Integer> mainPartIDColumn;
    @FXML
    private TableColumn<Part, String> mainPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> mainPartInvColumn;
    @FXML
    private TableColumn<Part, Double> mainPartPriceColumn;

    @FXML
    private Button searchPartsButton;
    @FXML
    private TextField searchPartsTextField;
    @FXML
    private Button clearPartsSearchButton;
    @FXML
    private Button addPartButton;
    @FXML
    private Button modifyPartButton;
    @FXML
    private Button deletePartButton;

    @FXML
    private TableView<Product> mainTableViewProduct;
    @FXML
    private TableColumn<Product, Integer> mainProductIDColumn;
    @FXML
    private TableColumn<Product, String> mainProductNameColumn;
    @FXML
    private TableColumn<Product, Integer> mainProductInvColumn;
    @FXML
    private TableColumn<Product, Double> mainProductPriceColumn;

    @FXML
    private Button searchProductsButton;
    @FXML
    private TextField searchProductsTextField;
    @FXML
    private Button clearProductsSearchButton;
    @FXML
    private Button addProductButton;
    @FXML
    private Button modifyProductButton;
    @FXML
    private Button deleteProductButton;

    /**
     *
     */
    @FXML
    private Button closeButton;

    /**
     *closes the program
     * @param event
     */
    @FXML
    public void closeProgram(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

        System.out.println("Closing Inventory Management System");

    }

//    launches add part screen
    @FXML
    public void addPartButtonClicked() throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Part");
        stage.show();
    }

//    launches add product screen
    @FXML
    public void addProductButtonClicked() throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Product");
        stage.show();
    }

//    launches modify part screen for selected part
    @FXML
    public void modifyPartButtonClicked() throws Exception {

        try {
            partToModify = mainTableViewPart.getSelectionModel().getSelectedItem();
            partToModifyID = partToModify.getPartID();
            Parent root = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Modify Part");
            stage.show();

        } catch (NullPointerException ex) {

        }

    }

//    launches modify product screen for selected product
    @FXML
    public void modifyProductButtonClicked() throws Exception {

        try {
            productToModify = mainTableViewProduct.getSelectionModel().getSelectedItem();
            productToModifyID = productToModify.getProductID();
            Parent root = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Modify Product");
            stage.show();
        } catch (NullPointerException ex) {

        }
    }

//    deletes selected part from inventory
    @FXML
    public void deletePartButtonClicked() throws Exception {

        try {
            Part part = mainTableViewPart.getSelectionModel().getSelectedItem();
            deletePart(part);
            updatePartsTable();
            searchPartsTextField.clear();
            System.out.println("Part " + part.getPartID() + " " + part.getName() + " was deleted.");
        } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {

        }

    }

    // deletes selected product from inventory
    @FXML
    public void deleteProductButtonClicked() throws Exception {

        try {
            Product product = mainTableViewProduct.getSelectionModel().getSelectedItem();
            removeProduct(product.getProductID());
            updateProductsTable();
            searchProductsTextField.clear();
            System.out.println("Product " + product.getProductID() + " " + product.getName() + " was deleted.");
        } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {

        }

    }

    //searches inventory for matching part name or ID
    @FXML
    public void searchPartButtonClicked() throws Exception {

        ObservableList<Part> tempParts = FXCollections.observableArrayList();
        ObservableList<Part> myList = getListOfParts();

        String searchTerm = searchPartsTextField.getText();

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

        mainTableViewPart.setItems(tempParts);
    }

    //searches inventory for matching product name or ID
    @FXML
    public void searchProductButtonClicked() throws Exception {

        ObservableList<Product> tempProducts = FXCollections.observableArrayList();
        ObservableList<Product> myList = getListOfProducts();

        String searchTerm = searchProductsTextField.getText();

        if (isSearchInteger(searchTerm) == true) {

            for (Product product : myList) {
                if (Integer.parseInt(searchTerm) == (product.getProductID())
                        || searchTerm.toUpperCase().trim().equals(product.getName().toUpperCase().trim())) {
                    tempProducts.add(product);
                }
            }

        } else {

            for (Product product : myList) {
                if (searchTerm.toUpperCase().trim().equals(product.getName().toUpperCase().trim())) {
                    tempProducts.add(product);
                }
            }
        }

        mainTableViewProduct.setItems(tempProducts);

    }

    //clears the search text field and resets tableview to display inventory rather than search results
    //not required but useful
    @FXML
    public void clearPartsSearchButtonClicked() throws Exception {

        updatePartsTable();
        searchPartsTextField.clear();
    }

    //clears the search text field and resets tableview to display inventory rather than search results
    //not required but useful
    @FXML
    public void clearProductsSearchButtonClicked() throws Exception {

        updateProductsTable();
        searchProductsTextField.clear();
    }

    public static boolean isSearchInteger(String isItInteger) {

        try {
            Integer.parseInt(isItInteger);
            return true;

        } catch (NumberFormatException ex) {

            return false;
        }

    }

    public static Part getPartToModify() {

        return partToModify;
    }

    public static int getPartToModifyID() {
        return partToModifyID;
    }

    public static Product getProductToModify() {

        return productToModify;
    }

    public static int getProductToModifyID() {

        return productToModifyID;
    }

    private void updatePartsTable() {

        mainTableViewPart.setItems(getListOfParts());

    }

    private void updateProductsTable() {

        mainTableViewProduct.setItems(getListOfProducts());
    }

    //sets the table views to show overall part and product inventory
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        mainPartIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        mainPartNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        mainPartInvColumn.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
        mainPartPriceColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
        updatePartsTable();

        mainProductIDColumn.setCellValueFactory(cellData -> cellData.getValue().productIDProperty().asObject());
        mainProductNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        mainProductInvColumn.setCellValueFactory(cellData -> cellData.getValue().productInvProperty().asObject());
        mainProductPriceColumn.setCellValueFactory(cellData -> cellData.getValue().productPriceProperty().asObject());
        updateProductsTable();

    }

}
