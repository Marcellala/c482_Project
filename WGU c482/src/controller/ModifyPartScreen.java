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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartScreen implements Initializable {
    public Label modifyPartLabel;
    public Button saveModifyButton;
    public Button cancelButton;
    public RadioButton inHouseRadio;
    public RadioButton outsourcedRadio;
    public Label machineLabel;
    public TextField modifyNameField;
    public TextField modifyInvField;
    public TextField modifyPriceField;
    public TextField modifyMaxField;
    public TextField modifyMachineIDField;
    public TextField modifyMinField;

    int partIndex;
    private InHouse inHousePart;
    private Outsourced outsourcedPart;

    /*An example of a runtime error I encountered can be found in the onSaveModifyButton. prior to parsing the data, I
    was unable to save the data that was input. By parsing the data in each field, I was able to convert the data to
    the correct type.
*/

    @Override
    /**
     * initializes controller
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     *  sets fields to screen and creates a copy of in house and outsourced part to modify
     */
    public void setInHouse(int partIndex, InHouse inHousePart)
    {
        this.partIndex = partIndex;
        machineLabel.setText("Machine ID");
        this.inHousePart = new InHouse(inHousePart.getId(), inHousePart.getName(), inHousePart.getPrice(), inHousePart.getStock(), inHousePart.getMin(), inHousePart.getMax(), inHousePart.getMachineId());
        inHouseRadio.setSelected(true);
        modifyNameField.setText(this.inHousePart.getName());
        modifyInvField.setText("" + this.inHousePart.getStock());
        modifyPriceField.setText("" + this.inHousePart.getPrice());
        modifyMinField.setText("" + this.inHousePart.getMin());
        modifyMaxField.setText("" + this.inHousePart.getMax());
        modifyMachineIDField.setText("" + this.inHousePart.getMachineId());
    }

    public void setOutsourced(int partIndex, Outsourced outsourcedPart)
    {
        this.partIndex = partIndex;
        machineLabel.setText("Company Name");
        this.outsourcedPart = new Outsourced(outsourcedPart.getId(), outsourcedPart.getName(), outsourcedPart.getPrice(), outsourcedPart.getStock(), outsourcedPart.getMin(), outsourcedPart.getMax(), outsourcedPart.getCompanyName());
        outsourcedRadio.setSelected(true);
        modifyNameField.setText("" + this.outsourcedPart.getName());
        modifyInvField.setText("" + this.outsourcedPart.getStock());
        modifyPriceField.setText("" + this.outsourcedPart.getPrice());
        modifyMinField.setText("" + this.outsourcedPart.getMin());
        modifyMaxField.setText("" + this.outsourcedPart.getMax());
        modifyMachineIDField.setText("" + this.outsourcedPart.getCompanyName());
    }

    /**
     * saves modifications to product and directs back to main screen
     * @param actionEvent
     * @throws IOException
     */
    public void onSaveModifyButton(ActionEvent actionEvent) throws IOException {
        boolean ok = inputValidation();
        if (!ok)
            return;
        String messageText = "";
        String partName = modifyNameField.getText();
        double partPrice = 0.0d;
        int partInventory = 0;
        int partMin = 0;
        int partMax = 0;
        try {
            partPrice = Double.parseDouble(modifyPriceField.getText());

        }
        catch (Exception e) {
        }

        try {
            partInventory = Integer.parseInt(modifyInvField.getText());

        }
        catch (Exception e) {
        }
        try {
            partMin = Integer.parseInt(modifyMinField.getText());

        }
        catch (Exception e) {
        }
        try {
            partMax = Integer.parseInt(modifyMaxField.getText());

        }
        catch (Exception e) {
        }

        if (inHouseRadio.isSelected()) {
            int partMachineID = 0;
            try {
                partMachineID = Integer.parseInt(modifyMachineIDField.getText());

            }
            catch (Exception e) {
            }
            this.inHousePart.setPrice(partPrice);
            this.inHousePart.setName(partName);
            this.inHousePart.setStock(partInventory);
            this.inHousePart.setMin(partMin);
            this.inHousePart.setMax(partMax);
            this.inHousePart.setMachineId(partMachineID);
            Inventory.updatePart(partIndex, this.inHousePart);
            inHousePart = null;
            partIndex = -1;
        }
        else
        {
            String companyName =modifyMachineIDField.getText();
            this.outsourcedPart.setPrice(partPrice);
            this.outsourcedPart.setName(partName);
            this.outsourcedPart.setStock(partInventory);
            this.outsourcedPart.setMin(partMin);
            this.outsourcedPart.setMax(partMax);
            this.outsourcedPart.setCompanyName(companyName);
            Inventory.updatePart(partIndex, this.outsourcedPart);
            outsourcedPart = null;
            partIndex = -1;
        }

        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,1000,400);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * button to cancel modification and directs back to main screen
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
     * sets label to machine ID or Company Name when switching between radio buttons
     * @param actionEvent
     */
    public void onInHouseRadio(ActionEvent actionEvent) {
        machineLabel.setText("Machine ID");
    }

    public void onOutsourcedRadio(ActionEvent actionEvent) {
        machineLabel.setText("Company Name");
    }

    /**
     * method to check input values are allowed
     */
    private boolean inputValidation () {
        String messageText = "";
        String modifyNameFieldText = modifyNameField.getText();
        int partInventory = 0;
        double partPrice = 0.0;
        try {
            partPrice = Double.parseDouble(modifyPriceField.getText());
        }
        catch (Exception e) {
            messageText += "Part Price must be numeric\n";
        }
        try{
            modifyNameFieldText = null;
        }
        catch (Exception e){
            messageText += "Part Name cannot be blank\n";
        }
        try {
            partInventory = Integer.parseInt(modifyNameField.getText());
        }
        catch (Exception e) {
            messageText += "Part inventory must be numeric\n";
        }
        try {
            int min = Integer.parseInt(modifyMinField.getText());
            int max = Integer.parseInt(modifyMaxField.getText());

            if (min > max) {
                messageText += "Part Min cannot be larger than part Max";
            }

            if (partInventory < min || partInventory > max) {
                messageText += "Inventory must be between min and max values";

            }
        } catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter a valid input for each text field before saving. No fields can be left blank");
            alert.showAndWait();
        }
        if (!messageText.isEmpty()) {
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

