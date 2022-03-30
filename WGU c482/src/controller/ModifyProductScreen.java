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
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyProductScreen implements Initializable {
    public Label modifyProductLabel;
    public Button cancelButton;
    public Button saveModifyProduct;
    public TableView partsTable;
    public TextField modifyPartSearch;
    public TableColumn partPriceCol;
    public TableColumn partInvCol;
    public TableColumn partNameCol;
    public TableColumn partIDCol;
    public TableView associatedPartTable;
    public TableColumn associatedPartIDCol;
    public TableColumn associatedPartName;
    public TableColumn associatedPartInvCol;
    public TableColumn associatedPartPriceCol;
    public Button addButton;
    public Button removeAssociatedPartButton;
    public TextField productNameField;
    public TextField productInvField;
    public TextField productPriceField;
    public TextField productMaxField;
    public TextField productMinField;
    public TextField productIDField;

    private Product passProduct;
    private Product product;
    int productIndex;

  /* An example of a runtime error that I encountered can be found in the Initialize method. Previously the
      PropertyValueFactory was not properly set to retrieve data. For example, the data member was named 'partName' the string passed to
      PropertyValueFactory was partName instead of Name */



    @Override
    /**
     * initializes controller and loads tableview data
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTable.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     *  sets fields to screen and creates a copy to modify
     */
    public void setProduct(int productIndex, Product passProduct)
    {
        this.productIndex = productIndex;
        this.passProduct = passProduct;
        this.product = new Product(passProduct.getId(), passProduct.getName(), passProduct.getPrice(), passProduct.getStock(), passProduct.getMin(), passProduct.getMax());
        this.product.setAssociatedParts(passProduct.getAllAssociatedParts());
        productNameField.setText(this.product.getName());
        productInvField.setText("" + this.product.getStock());
        productMinField.setText("" + this.product.getMin());
        productMaxField.setText("" + this.product.getMax());
        productPriceField.setText("" + this.product.getPrice());
        productIDField.setText("" + this.product.getId());
        associatedPartTable.setItems(this.product.getAllAssociatedParts());
    }

    /**
     * Button to cancel modification of product and return to main screen
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
     * saves product modifications and returns to main screen
     * @param actionEvent
     * @throws IOException
     */
    public void onSaveModifyProduct(ActionEvent actionEvent) throws IOException {
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

        product.setName(productName);
        product.setPrice(productPrice);
        product.setStock(productInventory);
        product.setMin(productMin);
        product.setMax(productMax);

        Inventory.updateProduct(productIndex, product);

        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,1000,400);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * method to search parts table on modify product screen by part name or ID. displays message if part not found
     * @param actionEvent
     */
    public void onModifyPartSearch(ActionEvent actionEvent) {
        String q = modifyPartSearch.getText();
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
     * adds associated part to product
     * @param actionEvent
     */
    public void onAddButton(ActionEvent actionEvent) {
        Part partSelection = (Part) partsTable.getSelectionModel().getSelectedItem();
        product.addAssociatedPart(partSelection);
    }

    /**
     * removes associated part from product
     * @param actionEvent
     */
    public void onRemoveAssociatedPartButton(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to remove this association?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            passProduct.deleteAssociatedPart((Part) associatedPartTable.getSelectionModel().getSelectedItem());
            product.deleteAssociatedPart((Part) associatedPartTable.getSelectionModel().getSelectedItem());

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
