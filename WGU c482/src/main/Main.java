package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

/*
Java docs folder is located in WGU c482 folder
 */

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root, 1000, 400));
        stage.show();

    }

    public static void main (String[] args){
    Inventory.addTestData();
        launch(args);}
}
