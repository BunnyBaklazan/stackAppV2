package com.example.stackapp.controller;

import com.example.stackapp.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class SampleController {

    private static final Main window = new Main();


   /* @FXML
    private Button b1;
    @FXML
    private Button exitBtn;
    @FXML
    private ImageView cornerExitBtn;*/
    String boxID;

    @FXML
    private Pane sampleAppPane;
    @FXML
    private Pane searchBoxPane;
    @FXML
    private TextField searchField;
    @FXML
    private Button showSearchBox_Btn;



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
    //Coment
    //
    /*BIG*/

}
