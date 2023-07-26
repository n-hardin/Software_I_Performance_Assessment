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

public class modifyProductController implements Initializable {
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
    public TextField modifyProductId = new TextField();
    public TextField modifyProductName = new TextField();
    public TextField modifyProductInv = new TextField();
    public TextField modifyProductCost = new TextField();
    public TextField modifyProductMax = new TextField();
    public TextField modifyProductMin = new TextField();
    public TextField partSearchField = new TextField();
    public Button saveButton = new Button();
    public Button cancelButton = new Button();
    private Product selectedProduct;

    /**
     * method to associate part
     * @param event clicking add button on modify product screen
     */
    public void addAssociated(ActionEvent event){
        Part selected = partsTable.getSelectionModel().getSelectedItem();
        if(selected == null){
            System.out.println("No part selected, please select a part to associate");
        }else{
            selectedProduct.addAssociatedPart(selected);
            associatedParts.setItems(selectedProduct.getAllAssociatedParts());;
        }

        System.out.println("Added " + selected.getName());
        event.consume();
    }

    /**
     * method to remove associated part from bottom table
     * @param event clicking remove button on modify product screen
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
                selectedProduct.deleteAssociatedPart(selected);
            }
        }
        System.out.println(selected.getName() + " deleted.");
        event.consume();
    }

    /**
     * LOGICAL ERROR: any characters could be input into fields and saved, leaving the program to crash if the user put
     * a space after their inventory level, for example -- this was corrected by adding try/catch loop to make sure
     * appropriate characters were in the appropriate fields
     * method to add a product to the products table
     * @param event clicking save button on modify product screen
     */
    public void modifyProduct(ActionEvent event) throws IOException {

        try {
            int newMax = Integer.parseInt(modifyProductMax.getText());
            int newMin = Integer.parseInt(modifyProductMin.getText());
            int newInv = Integer.parseInt(modifyProductInv.getText());
            if (newMin > newMax) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Minimum cannot be larger than maximum");
                alert.show();
            } else if (newMin <= newInv && newMax >= newInv) {
                Product newProduct = new Product(Integer.parseInt(modifyProductId.getText()), modifyProductName.getText(),
                        Double.parseDouble(modifyProductCost.getText()), newInv, newMin, newMax);
                Inventory.updateProduct(Inventory.getAllProducts().indexOf(selectedProduct), newProduct);
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
     * method for cancel button on modify product screen
     * @param event clicking cancel button on modify product screen
     * @throws IOException IOException
     */
    public void modifyProductCancel(ActionEvent event) throws IOException {
        Stage startPage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(programApplication.class.getResource("startPage.fxml"));
        startPage.setScene(new Scene(fxmlLoader.load()));
        startPage.show();
        startPage.setMaximized(true);
        cancelButton.getScene().getWindow().hide();
        event.consume();
    }

    /**
     * retrieves selected product from start page
     * @param product product to get
     */
    public void getProduct(Product product){
        selectedProduct = product;
        modifyProductId.setText((String.valueOf(product.getId())));
        modifyProductName.setText(product.getName());
        modifyProductInv.setText(String.valueOf(product.getStock()));
        modifyProductCost.setText(String.valueOf(product.getPrice()));
        modifyProductMax.setText(String.valueOf(product.getMax()));
        modifyProductMin.setText(String.valueOf(product.getMin()));
        modifyProductId.setText(String.valueOf(selectedProduct.getId()));
        modifyProductId.setDisable(true);
        associatedParts.setItems(selectedProduct.getAllAssociatedParts());
    }

    /**
     * searches part from text entered in search box
     * @param event pressing enter key while search field is selected
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

    }
}
