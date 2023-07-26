package controllers;

import com.example.pa.*;
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

public class modifyPartController {

    public RadioButton radioIH = new RadioButton();
    public RadioButton radioOS = new RadioButton();
    public TextField modifyPartId = new TextField();
    public TextField modifyPartName = new TextField();
    public TextField modifyPartInv = new TextField();
    public TextField modifyPartCost = new TextField();
    public TextField modifyPartMax = new TextField();
    public TextField modifyPartMin = new TextField();
    public TextField modifyPartExtra = new TextField();
    public Text machineCompany = new Text();
    public Button saveButton = new Button();
    public Button cancelButton = new Button();
    private Part selected;

    /**
     * method to modify a part
     * @param event clicking save button on modify part screen
     */
    public void modifyPart(ActionEvent event) throws IOException {

        try{
            int newMin = Integer.parseInt(modifyPartMin.getText());
            int newMax = Integer.parseInt(modifyPartMax.getText());
            int newInv = Integer.parseInt(modifyPartInv.getText());

            if (newMin > newMax) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Minimum cannot be larger than maximum");
                alert.show();
            } else if (newMin <= newInv && newMax >= newInv) {
                if (radioIH.isSelected()) {
                    InHouse newIH = new InHouse(Integer.parseInt(modifyPartId.getText()), modifyPartName.getText(),
                            Double.parseDouble(modifyPartCost.getText()), newInv, newMin, newMax);
                    newIH.setMachineId(Integer.parseInt(modifyPartExtra.getText()));
                    Inventory.updatePart(Inventory.getAllParts().indexOf(selected), newIH);
                } else if (radioOS.isSelected()) {
                    Outsourced newOS = new Outsourced(Integer.parseInt(modifyPartId.getText()), modifyPartName.getText(),
                            Double.parseDouble(modifyPartCost.getText()), newInv, newMin, newMax);
                    newOS.setCompanyName(modifyPartExtra.getText());
                    Inventory.updatePart(Inventory.getAllParts().indexOf(selected), newOS);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please choose a mode using one of the radio buttons.");
                    alert.show();
                }
                Stage startPage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(programApplication.class.getResource("startPage.fxml"));
                startPage.setScene(new Scene(fxmlLoader.load()));
                startPage.show();
                saveButton.getScene().getWindow().hide();
                startPage.setMaximized(true);
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
     * @param event clicking in-house radio button on modify part screen
     */
    public void radioSwapIH(ActionEvent event){
        if(radioIH.isSelected()){
            radioIH.requestFocus();
            radioOS.setSelected(false);
            machineCompany.setText("Machine ID");
        }
        event.consume();
    }

    /**
     * method to switch to outsourced mode via radio button
     * @param event clicking outsourced radio button on modify part screen
     */
    public void radioSwapOS(ActionEvent event){
        if(radioOS.isSelected()){
            radioOS.requestFocus();
            radioIH.setSelected(false);
            machineCompany.setText("Company Name");
        }
        event.consume();
    }

    /**
     * method for cancel button on modify part screen
     * @param event clicking cancel button on modify part screen
     * @throws IOException IOException
     */
    public void modifyPartCancel(ActionEvent event) throws IOException {
        Stage startPage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(programApplication.class.getResource("startPage.fxml"));
        startPage.setScene(new Scene(fxmlLoader.load()));
        startPage.show();
        startPage.setMaximized(true);
        cancelButton.getScene().getWindow().hide();
        event.consume();
    }

    /**
     * retrieves selected part from start page
     * @param part part to get
     */
    public void getPart(Part part){
        selected = part;
        modifyPartId.setText((String.valueOf(part.getId())));
        modifyPartName.setText(part.getName());
        modifyPartInv.setText(String.valueOf(part.getStock()));
        modifyPartCost.setText(String.valueOf(part.getPrice()));
        modifyPartMin.setText(String.valueOf(part.getMin()));
        modifyPartMax.setText(String.valueOf(part.getMax()));

        if(part instanceof InHouse){
            radioIH.requestFocus();
            radioIH.setSelected(true);
            modifyPartExtra.setText(String.valueOf(((InHouse) part).getMachineId()));
            machineCompany.setText("Machine ID");
            modifyPartId.setDisable(true);

        }else if (part instanceof Outsourced){
            radioOS.requestFocus();
            radioOS.setSelected(true);
            modifyPartExtra.setText(String.valueOf(((Outsourced) part).getCompanyName()));
            machineCompany.setText("Company Name");
            modifyPartId.setDisable(true);

        }
    }

}
