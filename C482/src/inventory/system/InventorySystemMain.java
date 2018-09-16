/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *main class for inventory system program
 * @author Benjamin
 */
public class InventorySystemMain extends Application {

    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Inventory Management System");
        Parent root = FXMLLoader.load(getClass().getResource("View_Controller/MainScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        System.out.println("Starting Inventory Management System");
        System.out.println();
    }

    /**
     * @param args the command line arguments
     * main method for program
     */ 
    public static void main(String[] args) {
        launch(args);
    }

}
