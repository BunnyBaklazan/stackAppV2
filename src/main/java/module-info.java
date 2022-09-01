module com.example.stackapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.stackapp to javafx.fxml;
    exports com.example.stackapp;
    exports com.example.stackapp.controller;
    opens com.example.stackapp.controller to javafx.fxml;
    exports com.example.stackapp.pages;
    opens com.example.stackapp.pages to javafx.fxml;
}