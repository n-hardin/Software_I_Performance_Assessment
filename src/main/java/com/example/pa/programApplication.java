package com.example.pa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class programApplication extends Application{

    @Override
    public void start(Stage startStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(programApplication.class.getResource("startPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        startStage.setScene(scene);
        startStage.setMaximized(true);
        startStage.show();
    }

    /**
     * FUTURE ENHANCEMENT: when exiting or saving one form and opening a new form, the previous form is closed
     * JAVADOC LOCATION: com.example.pa/module-summary
     * @param args args
     */
    public static void main(String[] args){


        InHouse bolt = new InHouse(1, "Bolt", 0.06, 100, 50, 500);
        bolt.setMachineId(111);

        InHouse nut = new InHouse(2, "Nut", 0.04, 75, 50, 500);
        nut.setMachineId(222);

        Outsourced screw = new Outsourced(3, "Screw", 0.10, 125, 50, 500);
        screw.setCompanyName("Screws Inc.");

        Product tractor = new Product(1, "Tractor", 24999.99, 10, 2, 20);

        Product truck = new Product(2, "Truck", 7999.99, 25, 5, 50);

        Product plow = new Product(3, "Plow", 4999.99, 15, 10, 20);

        Inventory.addPart(bolt);
        Inventory.addPart(nut);
        Inventory.addPart(screw);
        Inventory.addProduct(tractor);
        Inventory.addProduct(truck);
        Inventory.addProduct(plow);


        launch();
    }
}