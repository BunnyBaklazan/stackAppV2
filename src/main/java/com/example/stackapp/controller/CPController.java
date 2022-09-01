package com.example.stackapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CPController  implements Initializable {
    @FXML
    private ChoiceBox<String> myChoiceBox;
    private String[] cells= {"B1", "B2", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8"};
    String myCell;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        myChoiceBox.getItems().addAll(cells);
        myCell= myChoiceBox.getValue();
    }


}
