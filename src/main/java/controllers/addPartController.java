package controllers;

import com.example.pa.InHouse;
import com.example.pa.Inventory;
import com.example.pa.Outsourced;
import com.example.pa.programApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class addPartController {

    public TextField addPartId = new TextField();
    public TextField addPartName = new TextField();
    public TextField addPartInv = new TextField();
    public TextField addPartCost = new TextField();
    public TextField addPartMax = new TextField();
    public TextField addPartMin = new TextField();
    public TextField addPartExtra = new TextField();
    public RadioButton radioIH = new RadioButton();
    public RadioButton radioOS = new RadioButton();
    public Button cancelButton = new Button();
    public Button saveButton = new Button();
    public Text machineCompany = new Text();
    private int newPartId = Inventory.getAllParts().size()+1;

    /**
     * method to add a part to the parts table
     * @param event clicking save on add part screen
     */
    public void addPart(ActionEvent event) throws IOException {

        try {
            int newId = Integer.parseInt(addPartId.getText());
            String newName = addPartName.getText();
            double newCost = Double.parseDouble(addPartCost.getText());
            int newInv = Integer.parseInt(addPartInv.getText());
            int newMax = Integer.parseInt(addPartMax.getText());
            int newMin = Integer.parseInt(addPartMin.getText());

            if (newMin > newMax) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Minimum cannot be larger than maximum");
                alert.show();
            } else if (newMin <= newInv && newMax >= newInv) {
                if (radioIH.isSelected()) {
                    int newMachine = Integer.parseInt(addPartExtra.getText());
                    InHouse newPart = new InHouse(newId, newName, newCost, newInv, newMin, newMax );
                    newPart.setMachineId(newMachine);
                    Inventory.addPart(newPart);
                    Stage startPage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(programApplication.class.getResource("startPage.fxml"));
                    startPage.setScene(new Scene(fxmlLoader.load()));
                    startPage.show();
                    startPage.setMaximized(true);
                    saveButton.getScene().getWindow().hide();
                } else if (radioOS.isSelected()) {
                    String newCompany = addPartExtra.getText();
                    Outsourced newPart = new Outsourced(newId, newName, newCost, newInv, newMin, newMax);
                    newPart.setCompanyName(newCompany);
                    Inventory.addPart(newPart);
                    Stage startPage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(programApplication.class.getResource("startPage.fxml"));
                    startPage.setScene(new Scene(fxmlLoader.load()));
                    startPage.show();
                    startPage.setMaximized(true);
                    saveButton.getScene().getWindow().hide();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please choose a mode using one of the radio buttons.");
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Inventory level must be between maximum and minimum.");
                alert.show();
            }
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please ensure all numeric values do not contain letters or other characters, and that all forms are filled");
            alert.showAndWait();
            return;
        }
        event.consume();
    }

    /**
     * method to switch to in-house mode via radio button
     * @param event clicking In-House radio button
     */
    public void radioSwapIH(ActionEvent event){
        if(radioIH.isSelected()){
            radioIH.requestFocus();
            radioOS.setSelected(false);
            machineCompany.setText("Machine ID");
            addPartId.setText(String.valueOf(newPartId));
            addPartId.setDisable(true);
        }
        event.consume();
    }

    /**
     * method to switch to outsourced mode via radio button
     * @param event clicking outsourced radio button
     */
    public void radioSwapOS(ActionEvent event){
        if(radioOS.isSelected()){
            radioOS.requestFocus();
            radioIH.setSelected(false);
            machineCompany.setText("Company Name");
            addPartId.setText(String.valueOf(newPartId));
            addPartId.setDisable(true);
        }
        event.consume();
    }

    /**
     * method for cancel button on add part screen
     * @param event clicking cancel button on add part form
     * @throws IOException IOException
     */
    public void addPartCancel(ActionEvent event) throws IOException {
        Stage startPage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(programApplication.class.getResource("startPage.fxml"));
        startPage.setScene(new Scene(fxmlLoader.load()));
        startPage.show();
        startPage.setMaximized(true);
        cancelButton.getScene().getWindow().hide();
        event.consume();
    }

}
