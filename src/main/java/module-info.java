module com.example.stackapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.prefs;
    requires jbcrypt;


    opens com.example.stackapp to javafx.fxml;
    exports com.example.stackapp;
    exports com.example.stackapp.controller;
    opens com.example.stackapp.controller to javafx.fxml;
    exports com.example.stackapp.pages;
    opens com.example.stackapp.pages to javafx.fxml;
    opens com.example.stackapp.model to javafx.base;
}