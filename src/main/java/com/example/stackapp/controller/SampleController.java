package com.example.stackapp.controller;

import com.example.stackapp.Main;
import com.example.stackapp.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.Arrays;

public class SampleController {
    private static final Main window = new Main();
    private final String ADMIN = "admin";
    private User user = new User("asd", "admin");
    int boxId=0;
    @FXML
    private Pane sampleAppPane, searchBoxPane;
    @FXML
    private Button b1, b2, d1, d2, d3, d4, d5, d6, d7, d8, showSearchBox_Btn, addWorkerBtn = new Button(), editBtn, requestBtn, destroyBtn, saveBtn, acceptBtn, acceptDestroyBtn;
    @FXML
    private TextField searchField = new TextField(), shelf_IDField, box_IDField, client_IDField, periodField, date_fromField, date_endField, weightField, fulfillmentField, statusField, noteField, palField, palDestroyField;
    @FXML
    private Text notificationTxt;
    @FXML
    private Label palLabel, enterPalNrLabel, palDestroyLabel, enterPalNrDestroyLabel;

    TextField[] textFields = {shelf_IDField, client_IDField, periodField, date_fromField, date_endField, weightField, fulfillmentField, statusField, noteField};

    /*@FXML
    private final TextField[] textFields = {shelf_IDField, client_IDField, periodField, date_fromField, date_endField, weightField, fulfillmentField, statusField, noteField};*/

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

    //check function to change color on press
   /* @FXML
    private void btnPressed() {
        //setStyle("-fx-background-color: #4e5558;");
        Button[] btnArr= {b1, b2, d1, d3, d4, d5, d6, d7, d8, showSearchBox_Btn, addWorkerBtn};
        for (int i = 0; i < btnArr.length; i++) {
            btnArr[i].setStyle("-fx-background-color: #ADB9BD;");
        }
    }

    private class MyEventHandler implements EventHandler<Event>{
        @Override
        public void handle(Event evt) {
            String id = ((Button) evt.getSource()).getId();
            //boldButtonOnClick(id);
        }

    private void addEvents() {
        Button[] btnArr= {b1, b2, d1, d3, d4, d5, d6, d7, d8, showSearchBox_Btn, addWorkerBtn};
        for (int i = 0; i < btnArr.length; i++) {
            btnArr[i].addEventHandler(MouseEvent.MOUSE_CLICKED, new MyEventHandler());
        }
    }*/

    /*@FXML
    private void btnReleased() {
        //setStyle("-fx-background-color: #AAB2BD;");
    }*/

    @FXML
    private void editBox() {
        TextField[] textFields = {shelf_IDField, client_IDField, periodField, date_fromField, date_endField, weightField, fulfillmentField, statusField, noteField};

        for (TextField it : textFields) {
            it.setEditable(true);
        }

        destroyBtn.setVisible(false);
        requestBtn.setVisible(false);
        editBtn.setVisible(false);
        saveBtn.setVisible(true);
        notificationTxt.setText("");
    }

    @FXML
    private void destroyBox() {
        palDestroyField.setVisible(true);
        palDestroyLabel.setVisible(true);
        enterPalNrDestroyLabel.setVisible(true);
        acceptDestroyBtn.setVisible(true);
        editBtn.setVisible(false);
        requestBtn.setVisible(false);
        destroyBtn.setVisible(false);
    }

    @FXML
    private void saveDestroyPalToDB() {
        palDestroyField.setVisible(false);
        palDestroyLabel.setVisible(false);
        enterPalNrDestroyLabel.setVisible(false);
        acceptDestroyBtn.setVisible(false);
        editBtn.setVisible(true);
        requestBtn.setVisible(true);
        destroyBtn.setVisible(true);
        notificationTxt.setStyle("-fx-fill: red;");
        notificationTxt.setText("The box has been successfully deleted and saved in the database at 'PAL[X]:"+palField.getText()+"'");
    }

    @FXML
    private void requestBox() {
        palField.setVisible(true);
        palLabel.setVisible(true);
        enterPalNrLabel.setVisible(true);
        acceptBtn.setVisible(true);
        editBtn.setVisible(false);
        requestBtn.setVisible(false);
        destroyBtn.setVisible(false);
    }

    @FXML
    private void saveReqestPalToDB() {
        palField.setVisible(false);
        palLabel.setVisible(false);
        enterPalNrLabel.setVisible(false);
        acceptBtn.setVisible(false);
        editBtn.setVisible(true);
        requestBtn.setVisible(true);
        destroyBtn.setVisible(true);
        notificationTxt.setStyle("-fx-fill: #2c6432;");
        notificationTxt.setText("The box has been successfully saved in the database at 'PAL:[R]"+palField.getText()+"'");
    }

    @FXML
    private void saveBoxtoDB() {
        TextField[] textFields = {shelf_IDField, client_IDField, periodField, date_fromField, date_endField, weightField, fulfillmentField, statusField, noteField};
        //need to add connection to DB to save edits
        String[] testTxtsArr= new String[textFields.length];
        for (int i = 0; i < textFields.length; i++) {
            if(shelf_IDField.getText().equals("") || client_IDField.getText().equals("") || noteField.getText().equals("No such record! Try again!")) {
                textFields[i].setEditable(false);
                notificationTxt.setStyle("-fx-fill: red;");
                notificationTxt.setText("Error, adding Box to the database");
            }else {
                testTxtsArr[i] = textFields[i].getText();
                textFields[i].setEditable(false);
                notificationTxt.setStyle("-fx-fill: #2c6432;");
                notificationTxt.setText("The box has been successfully saved in the database");
            }
        }
        System.out.println("Šī brīža array= "+Arrays.toString(testTxtsArr));
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
    private int onKeyTyped() {
        searchField.setTextFormatter(new TextFormatter<>(c -> {
            if(!c.getControlNewText().matches("\\d*"))
                return null;
            else
                return c;
        }));
        boxId= Integer.parseInt(searchField.getText());
        return boxId;
    }
    @FXML
    private void getBoxIdByUserInput(){
        //TextField[] textFields = {shelf_IDField, client_IDField, periodField, date_fromField, date_endField, weightField, fulfillmentField, statusField, noteField};
        //need to add connection to DB to save edits
        for (int i = 0; i < textFields.length; i++) {
            if(shelf_IDField.getText().equals("") || client_IDField.getText().equals("") || noteField.getText().equals("No such record! Try again!")) {
                requestBtn.setVisible(false);
                destroyBtn.setVisible(false);
            }else {
                requestBtn.setVisible(true);
                destroyBtn.setVisible(true);
            }
        }

        //int boxIdToSearch= boxId;
        System.out.println("User enters: ["+boxId+ "] BOX_id to search in DB");
        getFieldInputsFromDB();
        notificationTxt.setStyle("-fx-fill: #aba9a9;");
        notificationTxt.setText("Last search: "+boxId);
    }
    private void getFieldInputsFromDB() {
        TextField[] testFieldsArr = {shelf_IDField, client_IDField, periodField, date_fromField, date_endField, weightField, fulfillmentField, statusField, noteField};
        String[] testTxtsArr = {"D1BC7", "34", "10YEARS", "2015-08-12", "2025-08-12", "Light", "Half full", "Check", "Damage on right side"};
        if(searchField.getText().equals("1004")) {
            for (int i = 0; i < testFieldsArr.length; i++) {
                testFieldsArr[i].setText(testTxtsArr[i]);
                testFieldsArr[i].setStyle("-fx-text-fill: black; -fx-background-color:  #dce2e8;");
            }
            box_IDField.setText(searchField.getText());
        } else{
            for (TextField textField : testFieldsArr) {
                textField.setText("");
            }
            box_IDField.setText(""+boxId);
            noteField.setText("No such record! Try again!");
            noteField.setStyle("-fx-text-fill: red; -fx-background-color:  #dce2e8;");
        }
    }
}
