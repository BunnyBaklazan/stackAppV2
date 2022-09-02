package com.example.stackapp.controller;

import com.example.stackapp.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class SampleController {

    private static final Main window = new Main();

    String boxID;

    @FXML
    private Pane sampleAppPane, searchBoxPane;
    /*@FXML
    private Pane searchBoxPane;*/
    @FXML
    private TextField searchField;
    @FXML
    private Button b1, b2, d1, d2, d3, d4, d5, d6, d7, d8, showSearchBox_Btn, exitBtn;
    @FXML
    private Button 



    /*@FXML
    private void changeBtnColor() {

    }*/
    @FXML
    private void checkBtn() {
        System.out.println("Btn pressed");
    }
    @FXML
    private void exit() {
        System.out.println("Exit Btn Pressed");
    }

    @FXML
    private void changePanToSearchBoxPan() {
        System.out.println("Yes it is search box pane");
        sampleAppPane.setVisible(false); //sampleAppPane not shown
        searchBoxPane.setVisible(true); //searchBoxPane are shown
    }

    @FXML
    private int onKeyTyped(KeyEvent event) {
        //boxID = "";
        if (!event.getCharacter().matches("[0-9]")) {
            event.consume();
            searchField.backward();
            searchField.deleteNextChar();
        } else {
            boxID = searchField.getText();
        }
        return Integer.parseInt(boxID);
    }
    @FXML
    private void getBoxIdByUserInput(){
        searchField= new TextField();
        int boxIdToSearch= Integer.parseInt(boxID);
        System.out.println("User enters: ["+boxIdToSearch+ "] BOX_id to search in DB");
    }
}
