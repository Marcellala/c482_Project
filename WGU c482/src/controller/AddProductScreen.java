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
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddProductScreen implements Initializable {
    public Label addProductLabel;
    public Button saveAddProduct;
    public Button cancelButton;
    public TextField productIdField;
    public TextField productNameField;
    public TextField productInvField;
    public TextField productPriceField;
    public TextField productMaxField;
    public TextField productMinField;
    public TextField addProductSearch;
    public TableView partsTable;
    public TableColumn partIDCol;
    public TableColumn partInvCol;
    public TableColumn partNameCol;
    public TableColumn partPriceCol;
    public Button addButton;
    public TableView associatedPartTable;
    public TableColumn associatedPartIDCol;
    public TableColumn associatedPartName;
    public TableColumn associatedPartInvCol;
    public TableColumn associatedPartPriceCol;
    public Button removeAssociatedPart;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /*An example of a logical error that I corrected can be found in the addProductSearch method. Here, I changed the type of
    event handler for the part search to use the On Action approach instead of the onInputMethodTextChanges approach
*/
    @Override

    /**
     * initializes controller and adds parts to tableviews
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTable.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartTable.setItems(associatedParts);
        associatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * method to save product and add to product table on main screen
     * @param actionEvent
     * @throws IOException
     */
    public void onSaveAddProduct(ActionEvent actionEvent) throws IOException {
        boolean ok = inputValidation();
        if (!ok)
            return;
        String productName = productNameField.getText();
        double productPrice = 0.0d;
        int productInventory = 0;
        int productMin = 0;
        int productMax = 0;
        try {
            productPrice = Double.parseDouble(productPriceField.getText());

        }
        catch (Exception e) {
        }

        try {
            productInventory = Integer.parseInt(productInvField.getText());

        }
        catch (Exception e) {
        }
        try {
            productMin = Integer.parseInt(productMinField.getText());

        }
        catch (Exception e) {
        }
        try {
            productMax = Integer.parseInt(productMaxField.getText());

        }
        catch (Exception e) {
        }

        Product newProduct = new Product(-1, productName, productPrice, productInventory, productMin, productMax);

        /**
         * adds associated part to product
         */
        for (Part p : associatedParts) {
                newProduct.addAssociatedPart(p);
            }

        Inventory.addProduct(newProduct);

        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,1000,400);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * button to return to main screen
     * @param actionEvent
     * @throws IOException
     */
    public void onCancelButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,1000,400);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * method to search parts table on add product screen by part name or ID. displays message if part not found
     * @param actionEvent
     */
    public void onAddProductSearch(ActionEvent actionEvent) {
        String q = addProductSearch.getText();
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
            if (list.size() > 0) {
                partsTable.setItems(list);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Part is not found");

                alert.showAndWait();
            }

        }

    }

    /**
     * adds selected part to associated parts table making that part associated to the product being added
     * @param actionEvent
     */
    public void onAddButton(ActionEvent actionEvent) {
        Part partSelection = (Part) partsTable.getItems().get((partsTable.getSelectionModel().getSelectedIndex()));
        associatedParts.add(partSelection);
    }

    /**
     * removes the associated part from the product
     * @param actionEvent
     */
    public void onRemoveAssociatedPart(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to remove this association?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            associatedParts.remove(associatedPartTable.getSelectionModel().getSelectedIndex());
        }

    }

    /**
     * method to check input values are allowed
     */
    private boolean inputValidation (){
        String messageText = "";
        String productName = productNameField.getText();
        int productInventory = 0;
        double productPrice = 0.0;
        try {
            productPrice = Double.parseDouble(productPriceField.getText());
        }
        catch (Exception e) {
            messageText += "Product Price must be numeric\n";
        }
        try {
            productInventory = Integer.parseInt(productInvField.getText());
        }
        catch(Exception e) {
            messageText += "Product inventory must be numeric\n";
        }
        try {
            int min = Integer.parseInt(productMinField.getText());
            int max = Integer.parseInt(productMaxField.getText());

            if (min > max) {
                messageText += "Product Min cannot be larger than part Max";
            }

            if (productInventory < min || productInventory > max) {
                messageText += "Inventory must be between min and max values";

            }
        }
        catch (NumberFormatException e){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter a valid input for each text field before saving. No fields can be left blank");
            alert.showAndWait();
        }
        if (!messageText.isEmpty())

        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Input Validation");
            alert.setHeaderText(null);
            alert.setContentText(messageText);
            alert.showAndWait();
            return false;
        }

        return true;

    }
}
