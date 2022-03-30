package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import javax.xml.validation.ValidatorHandler;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/*An example of a runtime error that I corrected can be found in the inputValidation method. At first, I tried to
implement validation checks with just logic. After several attempts, I implemented the try and catch methods to check
the inputs and validate them successfully
*/

/**
 * controller for the Add part screen of the application
 */
public class AddPartScreen implements Initializable {
    public Label addPartLabel;
    public RadioButton inHouseRadio;
    public RadioButton outsourcedRadio;
    public Label idLabel;
    public Label nameLabel;
    public Label inventoryLabel;
    public Label priceLabel;
    public Label maxLabel;
    public Label machineLabel;
    public Label minLabel;
    public TextField idField;
    public TextField nameField;
    public TextField inventoryField;
    public TextField priceField;
    public TextField maxField;
    public TextField machineField;
    public TextField minField;
    public Button cancelButton;
    public Button savePartButton;


    @Override
    /**
     * initializes controller
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * method to save part and display in parts table on main screen of the application
     * @param actionEvent
     * @throws IOException
     */
    public void onSavePartButton(ActionEvent actionEvent) throws IOException {

        boolean ok = inputValidation();
        if (!ok)
            return;
        String partName = nameField.getText();
        double partPrice = 0.0d;
        int partInventory = 0;
        int partMin = 0;
        int partMax = 0;
        try {
           partPrice = Double.parseDouble(priceField.getText());

        }
        catch (Exception e) {
        }

        try {
            partInventory = Integer.parseInt(inventoryField.getText());

        }
        catch (Exception e) {
        }
        try {
            partMin = Integer.parseInt(minField.getText());

        }
        catch (Exception e) {
        }
        try {
            partMax = Integer.parseInt(maxField.getText());

        }
        catch (Exception e) {
        }

        if (inHouseRadio.isSelected()) {
            int partMachineID = 0;
            try {
                partMachineID = Integer.parseInt(machineField.getText());

            }
            catch (Exception e) {
            }
            Inventory.addPart(new InHouse(-1, partName, partPrice, partInventory, partMin, partMax,partMachineID));
        }
        else
        {
            String companyName = machineField.getText();
            Inventory.addPart(new Outsourced(-1,partName,partPrice,partInventory, partMin, partMax,companyName));

        }

        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,1000,400);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * button to leave Add part screen and return to main screen
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
     * method to check input values are allowed
     */
    private boolean inputValidation (){
        String messageText = "";
        String partName = nameField.getText();
        int partInventory = 0;
        double partPrice = 0.0;
        try {
            partPrice = Double.parseDouble(priceField.getText());
        }
        catch (Exception e) {
            messageText += "Part Price must be numeric\n";
        }
        try {
            partInventory = Integer.parseInt(inventoryField.getText());
        }
        catch(Exception e) {
            messageText += "Part inventory must be numeric\n";
        }
        try {
            int min = Integer.parseInt(minField.getText());
            int max = Integer.parseInt(maxField.getText());

            if (min > max) {
                messageText += "Part Min cannot be larger than part Max";
            }

            if (partInventory < min || partInventory > max) {
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

    /**
     * sets label name to 'Machine ID' for in-house and 'Company Name' for Outsourced
     * @param actionEvent
     */
    public void onInHouseRadio(ActionEvent actionEvent) {
        machineLabel.setText("Machine ID");
    }

    public void onOutsourcedRadio(ActionEvent actionEvent) {
        machineLabel.setText("Company Name");
    }
}
