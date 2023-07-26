module com.example.pa {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pa to javafx.fxml;
    exports com.example.pa;
    exports controllers;
}