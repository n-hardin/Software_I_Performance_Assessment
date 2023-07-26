package com.example.pa;
/**
 *
 * @author Nicolas Hardin
 *
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * @param newPart the part to add
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * @param newProduct the product to add
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * @param partId the id of the part to look up
     * @return part
     */
    public static Part lookupPart(int partId){
        ObservableList<Part> allParts = Inventory.getAllParts();

        for (Part tempPart : allParts) {
            if (tempPart.getId() == partId) {
                return tempPart;
            }
        }
        return null;
    }

    /**
     * @param productId the id of the product to look up
     * @return product
     */
    public static Product lookupProduct(int productId){
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for (Product tempProd : allProducts) {
            if (tempProd.getId() == productId) {
                return tempProd;
            }
        }
        return null;
    }

    /**
     * @param partName name of the part to look up
     * @return part
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> partNames = FXCollections.observableArrayList();

        for(Part temp : Inventory.getAllParts()){
            if(temp.getName().contains(partName)){
                partNames.add(temp);
            }
        }
        return partNames;
    }

    /**
     * @param productName the name of the product to look up
     * @return product
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> prodNames = FXCollections.observableArrayList();

        for(Product temp : Inventory.getAllProducts()){
            if(temp.getName().contains(productName)){
                prodNames.add(temp);
            }
        }
        return prodNames;
    }

    /**
     * @param index index of the part to update
     * @param selectedPart part to set the index to
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    /**
     * @param index the index of the product to update
     * @param newProduct product to set the index to
     */
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }

    /**
     * @param selectedPart the part to delete
     * @return true
     */
    public static boolean deletePart(Part selectedPart){
        allParts.remove(selectedPart);
        return true;
    }

    /**
     * @param selectedProduct the product to delete
     * @return true
     */
    public static boolean deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);
        return true;
    }

    /**
     * @return list of all parts
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     * @return list of all products
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

}
