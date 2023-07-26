package controllers;

import com.example.pa.Inventory;
import com.example.pa.Part;
import com.example.pa.Product;
import com.example.pa.programApplication;
import javafx.application.Platform;
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

public class startPageController implements Initializable {

    @FXML
    public TableView<Part> partsTable = new TableView<>();
    public TableView<Product> productsTable = new TableView<>();
    public TableColumn partIdColumn = new TableColumn<>();
    public TableColumn partNameColumn= new TableColumn<>();
    public TableColumn partInvColumn= new TableColumn<>();
    public TableColumn partCostColumn= new TableColumn<>();
    public TableColumn prodIdColumn= new TableColumn<>();
    public TableColumn prodNameColumn= new TableColumn<>();
    public TableColumn prodInvColumn= new TableColumn<>();
    public TableColumn prodCostColumn= new TableColumn<>();
    public TextField partSearchField = new TextField();
    public TextField prodSearchField = new TextField();
    public Button addPartButton = new Button();
    public Button modPartButton = new Button();
    public Button addProdButton = new Button();
    public Button modProdButton = new Button();


    /**
     * opens add part menu
     * @param event clicking add button under parts table
     * @throws IOException IOException
     */
    public void openAddPart(ActionEvent event) throws IOException {
        Stage addPartStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(programApplication.class.getResource("addPart.fxml"));
        addPartStage.setScene(new Scene(fxmlLoader.load()));
        addPartStage.show();
        addPartButton.getScene().getWindow().hide();
        event.consume();
    }

    /**
     * opens modify part menu
     * @param event clicking modify button under parts table
     * @throws IOException IOException
     */
    public void openModifyPart(ActionEvent event) throws IOException {
        Part selected = partsTable.getSelectionModel().getSelectedItem();
        if(selected == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No part selected, please selected a part to modify");
            alert.show();
        }else{
            Stage modifyPartStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(programApplication.class.getResource("modifyPart.fxml"));
            modifyPartStage.setScene(new Scene(fxmlLoader.load()));
            modifyPartStage.show();
            modPartButton.getScene().getWindow().hide();
            modifyPartController modifyPartController = fxmlLoader.getController();
            modifyPartController.getPart(selected);
        }
        event.consume();
    }

    /**
     * deletes part from table
     * @param event clicking delete button under parts table
     */
    public void deletePart(ActionEvent event){
        Part selected = partsTable.getSelectionModel().getSelectedItem();
        if(selected == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No part selected, please selected a part to delete");
            alert.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete " + selected.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                Inventory.deletePart(selected);
                partsTable.setItems(Inventory.getAllParts());
            }
        }
        event.consume();
    }

    /**
     * searches part from text entered in search box
     * @param event pressing enter key while search field above parts table is selected
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
     * opens add product screen
     * @param event clicking add button under products table
     * @throws IOException IOException
     */
    public void openAddProduct(ActionEvent event) throws IOException {
        Stage addProductStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(programApplication.class.getResource("addProduct.fxml"));
        addProductStage.setScene(new Scene(fxmlLoader.load()));
        addProductStage.show();
        addProdButton.getScene().getWindow().hide();
        event.consume();
    }

    /**
     * opens modify product screen
     * @param event clicking modify button under products table
     * @throws IOException IOException
     */
    public void openModifyProduct(ActionEvent event) throws IOException {
        Product selected = productsTable.getSelectionModel().getSelectedItem();
        if(selected == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No product selected, please selected a product to modify");
            alert.show();
        }else{
            Stage modifyProductStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(programApplication.class.getResource("modifyProduct.fxml"));
            modifyProductStage.setScene(new Scene(fxmlLoader.load()));
            modifyProductStage.show();
            modProdButton.getScene().getWindow().hide();
            modifyProductController modifyProductController = fxmlLoader.getController();
            modifyProductController.getProduct(selected);
        }
        event.consume();
    }

    /**
     * deletes product from table
     * @param event clicking delete button under products table
     */
    public void deleteProduct(ActionEvent event){
        Product selected = productsTable.getSelectionModel().getSelectedItem();
        if(selected == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No product selected, please selected a product to delete");
            alert.show();
        }else{
            if(selected.getAllAssociatedParts() != null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot delete a product that has parts associated with it.");
                alert.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Are you sure you want to delete " + selected.getName() + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {
                    Inventory.deleteProduct(selected);
                    productsTable.setItems(Inventory.getAllProducts());
                }
            }
        }
        event.consume();
    }

    /**
     * searches product from text entered in search box
     * @param event pressing enter key while search field above products table is selected
     */
    public void searchProducts(ActionEvent event){
        String query = prodSearchField.getText();
        ObservableList<Product> products = Inventory.lookupProduct(query);

        if(products.size() == 0){
            try{
                int productId = Integer.parseInt(query);
                Product tempProd = Inventory.lookupProduct(productId);
                if(tempProd != null){
                    products.add(tempProd);
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
        productsTable.setItems(products);
        prodSearchField.setText("");
        event.consume();
    }

    /**
     * closes the application
     * @param event pressing exit button on start page
     */
    public void closeApplication(ActionEvent event){
        Platform.exit();
        System.out.println("Application Closed");
        event.consume();
    }

    /**
     * initializes columns on start page
     * @param url url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
        prodIdColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        prodNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        prodInvColumn.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        prodCostColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
        partsTable.setItems(Inventory.getAllParts());
        productsTable.setItems(Inventory.getAllProducts());
    }
}
