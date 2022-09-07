package com.example.stackapp.controller;

import com.example.stackapp.model.BoxData;
import com.example.stackapp.model.User;
import connect.net.sqlite.Connect;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Pane;

import java.time.Period;
import java.util.Arrays;

public class SampleController {
    //private static final Main window = new Main();
    private final String ADMIN = "admin";
    private User user = new User("boreil", "admin");

    long boxId = 1;
    @FXML
    private Pane sampleAppPane, searchBoxPane;
    @FXML
    private Button b1, b2, d1, d2, d3, d4, d5, d6, d7, d8, showSearchBox_Btn, editBtn, requestBtn, destroyBtn, saveBtn;
    @FXML
    private TextField searchField = new TextField(), shelf_IDField, box_IDField, client_IDField, periodField, date_fromField, date_endField, weightField, fulfillmentField, statusField, noteField;
    @FXML
    private Button addWorkerBtn = new Button();

    @FXML
    public void initialize() {
        addWorkerBtn.setVisible(false);

        if (user.getRole().equals(ADMIN)) {
            System.out.println("INSIDE");
            addWorkerBtn.setVisible(true);
        }
        System.out.println(addWorkerBtn.isVisible());

    }

    public SampleController () {
        System.out.println("LOADING CONSTRUCTOR");
        System.out.println("LOADING CONSTRUCTOR " + user.getRole());
    }

    @FXML
    private void btnPressed() {
        //setStyle("-fx-background-color: #4e5558;");
    }

    @FXML
    private void btnReleasedbtnReleased() {
        //setStyle("-fx-background-color: #AAB2BD;");
    }
    @FXML
    private void editBox() {
        TextField[] testFieldsArr = {shelf_IDField, client_IDField, periodField, date_fromField, date_endField, weightField, fulfillmentField, statusField, noteField};
        for (int i = 0; i < testFieldsArr.length; i++) {
            testFieldsArr[i].setEditable(true);
        }
        editBtn.setVisible(false);
        saveBtn.setVisible(true);
    }

    @FXML
    private void saveBoxtoDB() {
        //need to add connection to DB to save edits
        TextField[] testFieldsArr = {shelf_IDField, client_IDField, periodField, date_fromField, date_endField, weightField, fulfillmentField, statusField, noteField};
        String[] testTxtsArr= new String[testFieldsArr.length];
        for (int i = 0; i < testFieldsArr.length; i++) {
            testTxtsArr[i]= testFieldsArr[i].getText();
        }
        System.out.println(Arrays.toString(testTxtsArr));
        saveBtn.setVisible(false);
        editBtn.setVisible(true);
    }
    @FXML
    private void checkBtn() {
        System.out.println("Btn pressed");
    }
    @FXML
    private void exit() {
//        System.out.println("Exit Btn Pressed");
//        addWorkerBtn.setVisible(true);
    }

    @FXML
    private void changePanToSearchBoxPan() {
        System.out.println("Yes it is search box pane");
        sampleAppPane.setVisible(false); //sampleAppPane not shown
        searchBoxPane.setVisible(true); //searchBoxPane are shown
    }

    @FXML
    private long onKeyTyped() {
        searchField.setTextFormatter(new TextFormatter<>(c -> {
            if(!c.getControlNewText().matches("\\d*"))
                return null;
            else
                return c;
        }));
        //boxId = Long.parseLong(searchField.getText());
        return boxId;
    }
    @FXML
    private void getBoxIdByUserInput(){
        //int boxIdToSearch = boxId;
        System.out.println("User enters: ["+boxId+ "] BOX_id to search in DB");
        getFieldInputsFromDB();
    }


    private void getFieldInputsFromDB() {

        Connect conn = new Connect();
        if(conn.searchForBox(boxId) == null){
            System.out.println("We don't have that box");
            noteField.setText("No such record! Try again!");
            noteField.setStyle("-fx-text-fill: red; -fx-background-color:  #dce2e8;");
        } else {

            BoxData box = conn.searchForBox(boxId);
            //some problems with it
            Period period = Period.between(box.getDate_from().toLocalDate(), box.getDate_end().toLocalDate());
            
            //refactor later
            shelf_IDField.setText(box.getShelf_id());
            box_IDField.setText(Long.toString(boxId));
            shelf_IDField.setText(box.getShelf_id());
            client_IDField.setText(Long.toString(box.getClient_id()));
            periodField.setText(period.toString());
            date_fromField.setText(box.getDate_from().toString());
            date_endField.setText(box.getDate_end().toString());
            weightField.setText(box.getWeight());
            fulfillmentField.setText(box.getFulfillment());
            statusField.setText(box.getStatus());
            noteField.setText(box.getInfo_note());
        }



        /*searchField.setText(
                box.getShelf_id(),
                Long.toString(box.getBox_id()),
                Long.toString(box.getClient_id()),
                period.getYears(),
                box.getDate_from().toString(),
                box.getDate_end().toString(),
                box.getWeight(),
                box.getFulfillment(),
                box.getStatus(),
                box.getInfo_note()
        );*/


       /* if(searchField.getText().equals("1004")) {
            for (int i = 0; i < testFieldsArr.length; i++) {
                testFieldsArr[i].setText(testTxtsArr[i]);
                testFieldsArr[i].setStyle("-fx-text-fill: black; -fx-background-color:  #dce2e8;");
            }
            box_IDField.setText(searchField.getText());
        } else{
            for (int i = 0; i < testFieldsArr.length; i++) {
                testFieldsArr[i].setText("");
            }
            box_IDField.setText(""+boxId);
            noteField.setText("No such record! Try again!");
            noteField.setStyle("-fx-text-fill: red; -fx-background-color:  #dce2e8;");
        }*/
    }
}
