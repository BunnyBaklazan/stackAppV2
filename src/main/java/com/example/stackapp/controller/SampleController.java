package com.example.stackapp.controller;

import static com.example.stackapp.model.SampleUtils.calcPeriod;
import com.example.stackapp.model.BoxData;
import com.example.stackapp.model.User;
import connect.net.sqlite.Connect;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.Arrays;
public class SampleController{
    @FXML
    private CategoryAxis xAxis = new CategoryAxis();
    @FXML
    private NumberAxis yAxis = new NumberAxis();
    @FXML
    private BarChart<?, ?> bc = new BarChart<>(xAxis, yAxis);

    private static final String BOX_ID_VALIDATOR_MESSAGE = "Box ID must only contains numbers!";
    private static final String ONLY_NUMBERS_REGEX = "[0-9]*";
    private volatile boolean running = true;
    
    public long boxId = 1;
    private final String ADMIN = "admin";
    private User user = new User("asd", "admin");

    private int boxesAt1st = 1, boxesAt2nd = 2, boxesAt3rd = 3, boxesAt4th = 4, boxesAt5th = 5, boxesAt6th = 6, boxesAt7th = 7;

    @FXML
    private Pane sampleAppPane, searchBoxPane, addWorkerPane, b1Pane, b2Pane, d1Pane, b1ShelfPane;
    @FXML
    private Button b1Btn, b2Btn, d1Btn, d2Btn, d3Btn, d4Btn, d5Btn, d6Btn, d7Btn, d8Btn, showSearchBox_Btn, searchBtn, editBtn, requestBtn, destroyBtn, saveBtn, acceptBtn, acceptDestroyBtn, addWorkerBtn = new Button();
    @FXML
    private TextField shelfIDField, boxIDField, clientIDField, periodField, dateFromField, dateEndField, weightField, fulfillmentField, statusField, noteField, palRequestField, palDestroyField, searchField = new TextField();
    @FXML
    private Text notificationTxt;
    @FXML
    private Label leftCornerInfoLabel, titleTextLabel, palLabel, enterPalNrLabel, palDestroyLabel, enterPalNrDestroyLabel;
    @FXML
    private ImageView myBlackBox, myYellowBox, myBrownBox, myBlueBox, myRedBox, myPinkBox, myGreenBox, myPurpleBox, myOrangeBox;
    @FXML
    private ProgressIndicator secretProgressBar;

    LoadingScreen loadingScreen; //need to move down....test test

    public SampleController() {
        System.out.println("LOADING CONSTRUCTOR");
        System.out.println("LOADING CONSTRUCTOR " + user.getRole());
    }

    @FXML
    public void initialize() {
        leftCornerInfoLabel.setText("StackApp Choose Destination");
        sampleAppPane.setVisible(true);
        b1Pane.setVisible(false);
        searchBoxPane.setVisible(false);
        addWorkerPane.setVisible(false);
        b1ShelfPane.setVisible(false);
        b2Pane.setVisible(false);
        d1Pane.setVisible(false);

        ImageView[] myBoxImageArr = {myBlackBox, myBlueBox, myOrangeBox, myBrownBox, myPinkBox, myGreenBox, myPurpleBox, myRedBox, myYellowBox};
        for (int i = 0; i < myBoxImageArr.length; i++) {
            myBoxImageArr[i].setVisible(false);
        }
        loadingScreen = new SampleController.LoadingScreen(secretProgressBar, myRedBox);
        startAnimationProgress();

        addWorkerBtn.setVisible(false);
        if (user.getRole().equals(ADMIN)) {
            System.out.println("INSIDE");
            addWorkerBtn.setVisible(true);
        }
        System.out.println(addWorkerBtn.isVisible());
    }


    /**
     * ----    Default Panel    -----
     */
    @FXML
    private void changePanToDefaultPan() {
        leftCornerInfoLabel.setText("StackApp Choose Destination");
        sampleAppPane.setVisible(true);
        searchBoxPane.setVisible(false);
        addWorkerPane.setVisible(false);
        b1Pane.setVisible(false);
        b1ShelfPane.setVisible(false);
        b2Pane.setVisible(false);
        d1Pane.setVisible(false);
        restartAnimation();
    }

    @FXML
    private void checkBtn() {
        System.out.println("Btn pressed");
    }
    /**    ----END Default Panel END-----    */
//######################################################################################################################


    /**
     * ----    SearchBox Panel    -----
     */
    @FXML
    private void changePanToSearchBoxPan() {
        leftCornerInfoLabel.setText("StackApp SEARCH BOX");
        searchBoxPane.setVisible(true);
        sampleAppPane.setVisible(false);
        addWorkerPane.setVisible(false);
        b1Pane.setVisible(false);

        editBtn.setVisible(false);
        requestBtn.setVisible(false);
        destroyBtn.setVisible(false);
    }

    @FXML
    private void validateBoxId() {
        String userInput = searchField.getText();

        if (userInput.isEmpty() || !userInput.matches(ONLY_NUMBERS_REGEX)) {
            noteField.setText(BOX_ID_VALIDATOR_MESSAGE);
            searchBtn.setDisable(true);
        } else {
            noteField.clear();
            searchBtn.setDisable(false);
            System.out.println(userInput);

            boxId = Integer.parseInt(userInput);
        }
    }

    /*private int validateBoxId() {
        searchField.setTextFormatter(new TextFormatter<>(c -> {
            if(!c.getControlNewText().matches("\\d*"))
                return null;
            else
                return c;
        }));
        boxId= Integer.parseInt(searchField.getText());
        return boxId;
    }*/
    @FXML
    private void getBoxIdByUserInput() {
        TextField[] textFields = {shelfIDField, clientIDField, periodField, dateFromField, dateEndField, weightField, fulfillmentField, statusField, noteField};
        //need to add connection to DB to save edits
        for (int i = 0; i < textFields.length; i++) {
            if (shelfIDField.getText().equals("") || clientIDField.getText().equals("") || noteField.getText().equals("No such record! Try again!")) {
                requestBtn.setVisible(false);
                destroyBtn.setVisible(false);
            } else {
                requestBtn.setVisible(true);
                destroyBtn.setVisible(true);
            }
            acceptBtn.setVisible(false);
            acceptDestroyBtn.setVisible(false);
            saveBtn.setVisible(false);

            editBtn.setVisible(true);
        }
        System.out.println("User enters: [" + boxId + "] BOX_id to search in DB");
        //getFieldInputsFromDB();
        getBoxById(boxId);
        notificationTxt.setStyle("-fx-fill: #aba9a9;");
        notificationTxt.setText("Last search: " + boxId);
    }

    private void getFieldInputsFromDB() {
        TextField[] testFieldsArr = {shelfIDField, clientIDField, periodField, dateFromField, dateEndField, weightField, fulfillmentField, statusField, noteField};
        String[] testTxtsArr = {"D1BC7", "34", "10YEARS", "2015-08-12", "2025-08-12", "Light", "Half full", "Check", "Damage on right side"};
        if (searchField.getText().equals("1004")) {
            for (int i = 0; i < testFieldsArr.length; i++) {
                testFieldsArr[i].setText(testTxtsArr[i]);
                testFieldsArr[i].setStyle("-fx-text-fill: black; -fx-background-color:  #dce2e8;");
            }
            boxIDField.setText(searchField.getText());
        } else {
            for (TextField textField : testFieldsArr) {
                textField.setText("");
            }
            boxIDField.setText("" + boxId);
            noteField.setText("No such record! Try again!");
            noteField.setStyle("-fx-text-fill: red; -fx-background-color:  #dce2e8;");
        }
    }

    @FXML
    private void editBox() {
        TextField[] textFields = {shelfIDField, clientIDField, periodField, dateFromField, dateEndField, weightField, fulfillmentField, statusField, noteField};

        for (TextField it : textFields) {
            it.setEditable(true);
        }

        destroyBtn.setVisible(false);
        requestBtn.setVisible(false);
        editBtn.setVisible(false);
        saveBtn.setVisible(true);
        notificationTxt.setText("");
        onNoteEditChangeColor();
    }

    @FXML
    private void onBtnPress_DestroyBox() {
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

        editBtn.setVisible(true);
        requestBtn.setVisible(true);
        destroyBtn.setVisible(true);
        palDestroyField.setVisible(false);
        palDestroyLabel.setVisible(false);
        enterPalNrDestroyLabel.setVisible(false);
        acceptDestroyBtn.setVisible(false);
        notificationTxt.setStyle("-fx-fill: red;");
        notificationTxt.setText("The box has been successfully deleted and saved in the database at 'PAL[X]:" + palDestroyField.getText() + "'");
    }

    @FXML
    private void onBtnPress_RequestBox() {

        palRequestField.setVisible(true);
        palLabel.setVisible(true);
        enterPalNrLabel.setVisible(true);
        acceptBtn.setVisible(true);
        editBtn.setVisible(false);
        requestBtn.setVisible(false);
        destroyBtn.setVisible(false);
    }

    @FXML
    private void saveRequestPalToDB() {

        palRequestField.setVisible(false);
        palLabel.setVisible(false);
        enterPalNrLabel.setVisible(false);
        acceptBtn.setVisible(false);
        editBtn.setVisible(true);
        requestBtn.setVisible(true);
        destroyBtn.setVisible(true);
        notificationTxt.setStyle("-fx-fill: #2c6432;");
        notificationTxt.setText("The box has been successfully saved in the database at 'PAL:[R]" + palRequestField.getText() + "'");
    }

    @FXML
    private void saveBoxtoDB() {
        TextField[] textFields = {shelfIDField, clientIDField, periodField, dateFromField, dateEndField, weightField, fulfillmentField, statusField, noteField};
        //need to add connection to DB to save edits
        String[] testTxtsArr = new String[textFields.length];
        for (int i = 0; i < textFields.length; i++) {
            if (shelfIDField.getText().equals("") || clientIDField.getText().equals("") || noteField.getText().equals("No such record! Try again!")) {
                textFields[i].setEditable(false);
                notificationTxt.setStyle("-fx-fill: red;");
                notificationTxt.setText("Error, adding Box to the database");
            } else {
                testTxtsArr[i] = textFields[i].getText();
                textFields[i].setEditable(false);
                notificationTxt.setStyle("-fx-fill: #2c6432;");
                notificationTxt.setText("The box has been successfully saved in the database");
            }
        }
        System.out.println("Šī brīža array = " + Arrays.toString(testTxtsArr));
        saveBtn.setVisible(false);
        editBtn.setVisible(true);


    }

    @FXML
    void onNoteEditChangeColor() {
        noteField.setStyle("-fx-text-fill: BLACK; -fx-background-color:  #dce2e8");
    }



    private void getBoxById(long boxId) {
        TextField[] testFieldsArr = {shelfIDField, clientIDField, periodField, dateFromField, dateEndField, weightField, fulfillmentField, statusField, noteField};
        Connect conn = new Connect();
        BoxData box = conn.searchForBox(boxId);
        System.out.println("BoxId is " + boxId);
        if (box == null) {

            System.out.println("Is null");
            for (TextField textField : testFieldsArr) {
                textField.setText("");
            }

            boxIDField.setText("" + boxId);
            noteField.setText("No such record! Try again!");
            noteField.setStyle("-fx-text-fill: red; -fx-background-color:  #dce2e8;");

            System.out.println("We don't have that box");

        } else {
            noteField.setStyle("-fx-text-fill: BLACK; -fx-background-color:  #dce2e8");

            //refactor later
            shelfIDField.setText(box.getShelfId());
            boxIDField.setText(Long.toString(1));
            shelfIDField.setText(box.getShelfId());
            clientIDField.setText(Long.toString(box.getClient_id()));
            periodField.setText(calcPeriod(box.getDate_from(), box.getDate_end()));
            dateFromField.setText(box.getDate_from());
            dateEndField.setText(box.getDate_end());
            weightField.setText(box.getWeight());
            fulfillmentField.setText(box.getFulfillment());
            statusField.setText(box.getStatus());
            noteField.setText(box.getInfo_note());
            System.out.println("not null");
        }

    }
    
    /**    ----END SearchBox Panel END-----    */
//######################################################################################################################


    /**
     * ----    AddWorker Panel    -----
     */
    @FXML
    private void changePanToAddWorkerPan() {
        leftCornerInfoLabel.setText("StackApp WORKERS");
        addWorkerPane.setVisible(true);
        sampleAppPane.setVisible(false);
        searchBoxPane.setVisible(false);
        b1Pane.setVisible(false);
        b1ShelfPane.setVisible(false);
        b2Pane.setVisible(false);
        d1Pane.setVisible(false);
    }
    /**    ----END AddWorker Panel END-----    */
//######################################################################################################################


    /**
     * ----    B1 Panel    -----
     */
    @FXML
    private void changePanToB1Pan() {
        leftCornerInfoLabel.setText("StackApp Column- B1");
        b1Pane.setVisible(true);
        sampleAppPane.setVisible(false);
        searchBoxPane.setVisible(false);
        addWorkerPane.setVisible(false);
        b1ShelfPane.setVisible(false);
        b2Pane.setVisible(false);
        d1Pane.setVisible(false);
    }
    /**    ----END B1 Panel END-----    */
//######################################################################################################################


    /**
     * ----    B1 Panel    -----
     */
    @FXML
    private void changePanToB2Pan() {
        leftCornerInfoLabel.setText("StackApp Column- B1");
        b2Pane.setVisible(true);
        b1Pane.setVisible(false);
        sampleAppPane.setVisible(false);
        searchBoxPane.setVisible(false);
        addWorkerPane.setVisible(false);
        b1ShelfPane.setVisible(false);
        d1Pane.setVisible(false);
    }
    /**    ----END B1 Panel END-----    */
//######################################################################################################################


    /**
     * ----    B1 Panel    -----
     */
    @FXML
    private void changePanToD1Pan() {
        leftCornerInfoLabel.setText("StackApp Column- B1");
        d1Pane.setVisible(true);
        b1Pane.setVisible(false);
        sampleAppPane.setVisible(false);
        searchBoxPane.setVisible(false);
        addWorkerPane.setVisible(false);
        b1ShelfPane.setVisible(false);
        b2Pane.setVisible(false);

    }
    /**    ----END B1 Panel END-----    */
//######################################################################################################################


//######################################################################################################################

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
//######################################################################################################################

    /**
     * ----    Animation    -----
     */

    @FXML
    void startAnimationProgress() {
        Thread thread = new Thread(loadingScreen);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    void restartAnimation() {
        running = true;
        ImageView[] myBoxImageArr = {myBlackBox, myBlueBox, myOrangeBox, myBrownBox, myPinkBox, myGreenBox, myPurpleBox, myRedBox, myYellowBox};
        for (int i = 0; i < myBoxImageArr.length; i++) {
            myBoxImageArr[i].setVisible(false);
        }
        secretProgressBar.setProgress(0);
        startAnimationProgress();
    }

    @FXML
    void stopAnimation() {
        secretProgressBar.setProgress(0);
        running = false;

        ImageView[] myBoxImageArr = {myBlackBox, myBlueBox, myOrangeBox, myBrownBox, myPinkBox, myGreenBox, myPurpleBox, myRedBox, myYellowBox};
        for (int i = 0; i < myBoxImageArr.length; i++) {
            myBoxImageArr[i].setVisible(true);
        }

        myPinkBox.setLayoutX(188);
        myPinkBox.setLayoutY(248);
        myPinkBox.setRotate(0);
        myBlueBox.setLayoutX(354);
        myBlueBox.setLayoutY(248);
        myBlueBox.setRotate(0);

        myPurpleBox.setLayoutX(105);
        myPurpleBox.setLayoutY(372);
        myPurpleBox.setRotate(0);
        myOrangeBox.setLayoutX(263);
        myOrangeBox.setLayoutY(372);
        myOrangeBox.setRotate(0);
        myBrownBox.setLayoutX(421);
        myBrownBox.setLayoutY(372);
        myBrownBox.setRotate(0);

        myBlackBox.setLayoutX(22);
        myBlackBox.setLayoutY(495);
        myBlackBox.setRotate(0);
        myYellowBox.setLayoutX(180);
        myYellowBox.setLayoutY(495);
        myYellowBox.setRotate(0);
        myRedBox.setLayoutX(338);
        myRedBox.setLayoutY(495);
        myRedBox.setRotate(0);
        myGreenBox.setLayoutX(496);
        myGreenBox.setLayoutY(495);
        myGreenBox.setRotate(0);
    }

    public class LoadingScreen implements Runnable {
        ImageView[] myBoxImageArr = {myBlackBox, myBlueBox, myOrangeBox, myBrownBox, myPinkBox, myGreenBox, myPurpleBox, myRedBox, myYellowBox};

        ProgressIndicator secretProgressBar;
        ImageView img;

        public LoadingScreen(ProgressIndicator secretProgressBar, ImageView img) {
            this.secretProgressBar = secretProgressBar;
            this.img = img;
        }

        @Override
        public void run() {
            while (secretProgressBar.getProgress() <= 1 && running) {
                if (secretProgressBar.getProgress() >= 0.1 && secretProgressBar.getProgress() <= 0.15) {
                    myBoxImageArr[7].setVisible(true);
                } else if (secretProgressBar.getProgress() >= 0.15 && secretProgressBar.getProgress() <= 0.2) {
                    myBoxImageArr[0].setVisible(true);
                } else if (secretProgressBar.getProgress() >= 0.2 && secretProgressBar.getProgress() <= 0.25) {
                    myBoxImageArr[3].setVisible(true);
                } else if (secretProgressBar.getProgress() >= 0.25 && secretProgressBar.getProgress() <= 0.3) {
                    myBoxImageArr[1].setVisible(true);
                } else if (secretProgressBar.getProgress() >= 0.3 && secretProgressBar.getProgress() <= 0.35) {
                    myBoxImageArr[6].setVisible(true);
                } else if (secretProgressBar.getProgress() >= 0.35 && secretProgressBar.getProgress() <= 0.4) {
                    myBoxImageArr[4].setVisible(true);
                } else if (secretProgressBar.getProgress() >= 0.4 && secretProgressBar.getProgress() <= 0.45) {
                    myBoxImageArr[2].setVisible(true);
                } else if (secretProgressBar.getProgress() >= 0.45 && secretProgressBar.getProgress() <= 0.5) {
                    myBoxImageArr[5].setVisible(true);
                } else if (secretProgressBar.getProgress() >= 0.5 && secretProgressBar.getProgress() <= 0.55) {
                    myBoxImageArr[8].setVisible(true);
                } else if (secretProgressBar.getProgress() >= 0.55 && secretProgressBar.getProgress() <= 0.6) {
                    myBoxImageArr[7].setLayoutX(188);
                    myBoxImageArr[7].setLayoutY(248);
                } else if (secretProgressBar.getProgress() >= 0.6 && secretProgressBar.getProgress() <= 0.65) {
                    myBoxImageArr[0].setLayoutX(354);
                    myBoxImageArr[0].setLayoutY(248);
                } else if (secretProgressBar.getProgress() >= 0.65 && secretProgressBar.getProgress() <= 0.7) {
                    myBoxImageArr[3].setLayoutX(105);
                    myBoxImageArr[3].setLayoutY(372);
                } else if (secretProgressBar.getProgress() >= 0.7 && secretProgressBar.getProgress() <= 0.75) {
                    myBoxImageArr[1].setLayoutX(263);
                    myBoxImageArr[1].setLayoutY(372);
                } else if (secretProgressBar.getProgress() >= 0.75 && secretProgressBar.getProgress() <= 0.8) {
                    myBoxImageArr[6].setLayoutX(421);
                    myBoxImageArr[6].setLayoutY(372);
                } else if (secretProgressBar.getProgress() >= 0.8 && secretProgressBar.getProgress() <= 0.85) {
                    myBoxImageArr[4].setLayoutX(22);
                    myBoxImageArr[4].setLayoutY(495);
                } else if (secretProgressBar.getProgress() >= 0.85 && secretProgressBar.getProgress() <= 0.9) {
                    myBoxImageArr[2].setLayoutX(180);
                    myBoxImageArr[2].setLayoutY(495);
                } else if (secretProgressBar.getProgress() >= 0.9 && secretProgressBar.getProgress() <= 0.95) {
                    myBoxImageArr[5].setLayoutX(338);
                    myBoxImageArr[5].setLayoutY(495);
                } else if (secretProgressBar.getProgress() >= 0.95 && secretProgressBar.getProgress() <= 0.99) {
                    myBoxImageArr[8].setLayoutX(496);
                    myBoxImageArr[8].setLayoutY(495);
                }
                Platform.runLater(() -> secretProgressBar.setProgress(secretProgressBar.getProgress() + 0.01));
                synchronized (this) {
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        running = false;
                    }
                }
            }

        }
    }
    /**    ----END Animation END-----    */
//######################################################################################################################


    /**
     * ----    ShelfPanel Panel    -----
     */
    @FXML
    private void changePanToShelfPan() {
        setBarTable();
        leftCornerInfoLabel.setText("StackApp Shelf- B1-BB");
        b1ShelfPane.setVisible(true);
        b1Pane.setVisible(false);
        sampleAppPane.setVisible(false);
        searchBoxPane.setVisible(false);
        addWorkerPane.setVisible(false);
    }

    /**
     * ----END B1 Panel END-----
     */
//######################################################################################################################
    @FXML
    void setBarTable() {
        boxesAt1st = 5;
        boxesAt2nd = 9;
        boxesAt3rd = 5;
        boxesAt4th = 2;
        boxesAt5th = 7;
        boxesAt6th = 7;
        boxesAt7th = 3;

        bc.setVisible(true);
        bc.setTitle("D1BB");
        //xAxis.setLabel("BOXES");
        //yAxis.setLabel("UNITS");

        XYChart.Series boxes = new XYChart.Series();//, nr2= new XYChart.Series(), nr3= new XYChart.Series(), nr4= new XYChart.Series(), nr5= new XYChart.Series(), nr6= new XYChart.Series(), nr7= new XYChart.Series();
        boxes.getData().add(new XYChart.Data("1", boxesAt1st));
        boxes.getData().add(new XYChart.Data("2", boxesAt2nd));
        boxes.getData().add(new XYChart.Data("3", boxesAt3rd));
        boxes.getData().add(new XYChart.Data("4", boxesAt4th));
        boxes.getData().add(new XYChart.Data("5", boxesAt5th));
        boxes.getData().add(new XYChart.Data("6", boxesAt6th));
        boxes.getData().add(new XYChart.Data("7", boxesAt7th));
        bc.getData().addAll(boxes);
    }
}
