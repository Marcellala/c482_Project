package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.*;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/*An example of a logical error that I corrected can be found in the following methods:
1. onModifyPart
2. onModifyProduct
3. onDeletePart
4. onDeleteProduct
Here, I added an error message that displays if the user clicks 'Modify' or 'Delete' buttons without selecting a part or product first.

A future enhancement would redirect the user to the 'Modify Product' screen if the user tries to delete a product with associated parts.
This would elevate the user experience and make it easier to remove the associated parts prior to deleting.
*/


/**
 * Controller for the main screen of the application
 */
public class MainScreen implements Initializable {
    public Label titleLabel;
    public Button exitMain;
    public GridPane mainGrid;
    public Label partsLabel;
    public Label productsLabel;
    public TableView partsTable;
    public TableView productsTable;
    public Button addParts;
    public Button modifyPart;
    public Button deletePart;
    public Button addProduct;
    public Button modifyProduct;
    public Button deleteProduct;
    public TableColumn partIDCol;
    public TableColumn partNameCol;
    public TableColumn partInvCol;
    public TableColumn partPriceCol;
    public TableColumn productIDCol;
    public TableColumn productNameCol;
    public TableColumn productInvCol;
    public TableColumn productPriceCol;
    public TextField PartQuery;
    public TextField ProductQuery;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private Object Product;


    /**
     * populates the parts and products table view on main screen. Initializes controller
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTable.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(Inventory.getAllProducts());
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

    /**
     * method to exit the program with alert confirmation
     * @param actionEvent
     */
    public void onExitMain(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to exit the program?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    /**
     * method to load add part screen
     */
    public void onAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPartScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * gets part to modify from parts table and sends data to modify part screen for editing
     * @param actionEvent
     * @throws IOException
     */
    public void onModifyPart(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyPartScreen.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        ModifyPartScreen modifyPartScreen = loader.getController();
        Part selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Select a part from the table to modify");

            alert.showAndWait();

            return;
        }
        else
            if (selectedPart instanceof InHouse)
            {
                modifyPartScreen.setInHouse(partsTable.getSelectionModel().getSelectedIndex(), (InHouse) selectedPart);
            }
            else
            {
                modifyPartScreen.setOutsourced(partsTable.getSelectionModel().getSelectedIndex(), (Outsourced) selectedPart);
            }
        stage.setTitle("Modify Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * method to delete selected part from parts table. Displays message to confirm action
     * @param actionEvent
     */
    public void onDeletePart(ActionEvent actionEvent) {
        Part selection = (model.Part) partsTable.getSelectionModel().getSelectedItem();


        if (selection == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setContentText("Please select a part to delete");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(null);
            alert.setContentText("Are you sure you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK){

                Inventory.getAllParts().remove(selection);
            }

        }
    }

    /**
     *  method to load add product screen
     * @param actionEvent
     * @throws IOException
     */
    public void onAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProductScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * gets part to modify from products table and sends data to modify part screen for editing
     * @param actionEvent
     * @throws IOException
     */
    public void onModifyProduct(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyProductScreen.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 600);
        ModifyProductScreen modifyProductScreen = loader.getController();
        Product selectedProduct = (Product) productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null){
            Alert alert = new Alert(Alert.AlertType.WARNING.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Select a product from the table to modify");

            alert.showAndWait();

            return;
        }
       else{
            modifyProductScreen.setProduct(productsTable.getSelectionModel().getSelectedIndex(), (Product) selectedProduct);
        }
        stage.setTitle("Modify Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *  method to delete selected product from product table. Displays message to confirm
     * @param actionEvent
     */
    public void onDeleteProduct(ActionEvent actionEvent) {

        Product selection = (model.Product) productsTable.getSelectionModel().getSelectedItem();


        if (selection == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setContentText("Please select a product to delete");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(null);
            alert.setContentText("Are you sure you want to delete this product?");
            Optional<ButtonType> result = alert.showAndWait();

            ObservableList<Part> associatedParts = selection.getAllAssociatedParts();

            if (associatedParts.size() >= 1) {
                Alert alert1 = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setHeaderText(null);
                alert.setContentText("Product cannot be deleted with associated parts. Please remove associated parts from product and try again");

                alert.showAndWait();
            } else {
                Inventory.getAllProducts().remove(selection);
            }

        }

    }


    /**
     * Searches for parts by Part name or ID and displays message if part not found
     *
     * @param actionEvent
     */
    public void onPartQuery(ActionEvent actionEvent) {
        String q = PartQuery.getText();
        try {
            partsTable.getSelectionModel().clearSelection();
            if (q.isBlank()){
                partsTable.setItems(Inventory.getAllParts());
            }
            else{
                int partId = Integer.parseInt(q);
                Part p = Inventory.lookupPart(partId);
                if (p != null){
                    partsTable.getSelectionModel().select(p);
                }
                else{
                    throw new NumberFormatException();
                }
            }
        } catch (NumberFormatException e) {
            ObservableList<Part> list = Inventory.lookupPart(q);
            if(list.size() > 0){
                partsTable.setItems(list);
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Part is not found");

                alert.showAndWait();
            }

        }

    }

    /**
     * Searches for products by Product name or ID and displays message if product not found
     * @param actionEvent
     */
    public void onProductQuery(ActionEvent actionEvent) {
        String q = ProductQuery.getText();
        try {
            productsTable.getSelectionModel().clearSelection();
            if (q.isBlank()){
                productsTable.setItems(Inventory.getAllProducts());
            }
            else{
                int productID = Integer.parseInt(q);
                Product p = Inventory.lookupProduct(productID);
                if (p != null){
                   productsTable.getSelectionModel().select(p);
                }
                else{
                    throw new NumberFormatException();
                }
            }
        } catch (NumberFormatException e) {
            ObservableList<Product> list = Inventory.lookupProduct(q);
            if(list.size() > 0){
                productsTable.setItems(list);
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Product is not found");

                alert.showAndWait();
            }

        }
    }
}