/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.system.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * class that defines inventory system all variables and methods static as
 * they're called by other classes
 */
public class Inventory {

    //changed arraylist to observablelist as allowed by program instructions
    private static ObservableList<Product> listOfProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> listOfParts = FXCollections.observableArrayList();
    //additional variables that help with generating part and product IDs
    // part and product count is the ID for that part or product
    private static int productCount = 0;
    private static int partCount = 0;

    public Inventory() {
    }

    public static ObservableList<Part> getListOfParts() {
        return listOfParts;
    }

    public static ObservableList<Product> getListOfProducts() {
        return listOfProducts;
    }

    public static int getProductCount() {

        return productCount;
    }

    public static void increaseProductCount() {

        productCount++;
    }

    public static void addProduct(Product product) {
        listOfProducts.add(product);

    }

    public static boolean removeProduct(int ProductID) {

        Product toDelete = lookupProduct(ProductID);
        int myIndex = listOfProducts.indexOf(toDelete);
        listOfProducts.remove(myIndex);
        return true;

    }

    public static Product lookupProduct(int ProductID) {

        Product searchProduct = null;

        for (Product product : listOfProducts) {
            if (ProductID == product.getProductID()) {

                searchProduct = product;
            }

        }
        int myIndex = listOfProducts.indexOf(searchProduct);
        return listOfProducts.get(myIndex);

    }

    //skeleton code for unused required method
    //functionality handled in modifyproductcontroller.modifyproduct()
    public static void updateProduct(int ProductID) {

    }

    public static int getPartCount() {

        return partCount;
    }

    public static void increasePartCount() {

        partCount++;
    }

    public static void addPart(Part part) {

        listOfParts.add(part);

    }

    public static boolean deletePart(Part part) {

        int myIndex = listOfParts.indexOf(part);
        listOfParts.remove(myIndex);
        return true;

    }

    public static Part lookupPart(int PartID) {

        Part searchPart = null;

        for (Part part : listOfParts) {
            if (PartID == part.getPartID()) {

                searchPart = part;
            }

        }
        int myIndex = listOfParts.indexOf(searchPart);
        return listOfParts.get(myIndex);

    }

    //skeleton code for unused required method
    //functionality handled in modifypartcontroller.modifypart()
    public static void updatePart(int PartID) {

    }

}
