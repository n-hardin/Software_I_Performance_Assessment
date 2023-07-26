package controllers;

import com.example.pa.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class addProductController implements Initializable{

    @FXML
    public TableView<Part> partsTable = new TableView<>();
    public TableView<Part> associatedParts = new TableView<>();
    public TableColumn partsIdColumn = new TableColumn<>();
    public TableColumn partsNameColumn= new TableColumn<>();
    public TableColumn partsInvColumn= new TableColumn<>();
    public TableColumn partsCostColumn= new TableColumn<>();
    public TableColumn associatedIdColumn= new TableColumn<>();
    public TableColumn associatedNameColumn= new TableColumn<>();
    public TableColumn associatedInvColumn= new TableColumn<>();
    public TableColumn associatedCostColumn= new TableColumn<>();
    public TextField addProductId = new TextField();
    public TextField addProductName = new TextField();
    public TextField addProductInv = new TextField();
    public TextField addProductCost = new TextField();
    public TextField addProductMax = new TextField();
    public TextField addProductMin = new TextField();
    public TextField partSearchField = new TextField();
    public Button saveButton = new Button();
    public Button cancelButton = new Button();
    private int newProductId = Inventory.getAllProducts().size()+1;
    private Product newProduct = new Product(newProductId,"",0,0,0,0);


    /**
     * method to associate part
     * @param event clicking add button on add product screen
     */
    public void addAssociated(ActionEvent event){
        Part selected = partsTable.getSelectionModel().getSelectedItem();
        if(selected == null){
            System.out.println("No part selected, please select a part to associate");
        }else{
            newProduct.addAssociatedPart(selected);
            associatedParts.setItems(newProduct.getAllAssociatedParts());;
        }

        System.out.println("Added " + selected.getName());
        event.consume();
    }

    /**
     * method to add a product to the product table
     * @param event clicking save button on add product screen
     */
    public void addProduct(ActionEvent event) throws IOException {

        try{
            int newMin = Integer.parseInt(addProductMin.getText());
            int newMax = Integer.parseInt(addProductMax.getText());
            int newInv = Integer.parseInt(addProductInv.getText());

            if(newMin > newMax) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Minimum cannot be larger than maximum");
                alert.show();
            }else if(newMin <= newInv && newMax >= newInv) {
                newProduct.setId(Integer.parseInt(addProductId.getText()));
                newProduct.setName(addProductName.getText());
                newProduct.setStock(newInv);
                newProduct.setPrice(Double.parseDouble(addProductCost.getText()));
                newProduct.setMax(newMax);
                newProduct.setMin(newMin);
                Inventory.addProduct(newProduct);

                Stage startPage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(programApplication.class.getResource("startPage.fxml"));
                startPage.setScene(new Scene(fxmlLoader.load()));
                startPage.show();
                startPage.setMaximized(true);
                saveButton.getScene().getWindow().hide();
            }else{
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
     * method to remove associated part from bottom table
     * @param event clicking remove button on add product scree
     */
    public void deleteAssociated(ActionEvent event){
        Part selected = associatedParts.getSelectionModel().getSelectedItem();
        if(selected == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No part selected, please selected a part to remove");
            alert.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to remove " + selected.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                newProduct.deleteAssociatedPart(selected);
            }
        }

        System.out.println(selected.getName() + " deleted.");
        event.consume();
    }

    /**
     * method for cancel button on add product screen
     * @param event clicking cancel button on add product screen
     * @throws IOException IOException
     */
    public void addProductCancel(ActionEvent event) throws IOException {
        Stage startPage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(programApplication.class.getResource("startPage.fxml"));
        startPage.setScene(new Scene(fxmlLoader.load()));
        startPage.show();
        startPage.setMaximized(true);
        cancelButton.getScene().getWindow().hide();
        event.consume();
    }

    /**
     * searches part from text entered in search box
     * @param event
     */
    public void searchParts(ActionEvent event){
        String query = partSearchField.getText();
        ObservableList<Part> parts = Inventory.lookupPart(query);

        if(parts.size() == 0){
            try{
                int partId = Integer.parseInt(query);
                Part tempPart = Inventory.lookupPart(partId);
                if(tempPart != null){
                    parts.add(tempPart);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("No matches found, please ensure spelling and capitalization are correct");
                    alert.showAndWait();
                }
            }catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No matches found, please ensure spelling and capitalization are correct");
                alert.showAndWait();
            }

        }
        partsTable.setItems(parts);
        partSearchField.setText("");
        event.consume();
    }

    /**
     * initializes columns on tables
     * @param url url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsIdColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partsNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        partsInvColumn.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        partsCostColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
        associatedIdColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        associatedNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        associatedInvColumn.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        associatedCostColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
        partsTable.setItems(Inventory.getAllParts());
        addProductId.setText(String.valueOf(newProduct.getId()));
        addProductId.setDisable(true);
    }

}
