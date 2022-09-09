package com.example.stackapp.controller;

import static com.example.stackapp.model.SampleUtils.calcPeriod;
import static java.lang.String.valueOf;
import com.example.stackapp.model.BoxData;
import com.example.stackapp.model.User;
import connect.net.sqlite.Connect;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.util.Arrays;
import java.util.List;

public class SampleController {

    public static final String EMPTY_STRING = "";
    @FXML
    private CategoryAxis xAxis = new CategoryAxis();
    @FXML
    private NumberAxis yAxis = new NumberAxis(0, 9, 1);
    @FXML
    private BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

    private static final String BOX_ID_VALIDATOR_MESSAGE = "Box ID must only contains numbers!";
    private static final String ONLY_NUMBERS_REGEX = "[0-9]*";
    private volatile boolean running = true;

    public long boxId;
    private final String ADMIN = "admin";
    private User user = new User("asd", "admin");

    private int count1, count2, count3, count4, count5, count6, count7;

    @FXML
    private Pane sampleAppPane, searchBoxPane, addWorkerPane, b1Pane, b2Pane, d1Pane, shelfPane, addressPane;
    @FXML
    private Button b1Btn, b2Btn, d1Btn, d2Btn, d3Btn, d4Btn, d5Btn, d6Btn, d7Btn, d8Btn, showSearchBox_Btn, searchBtn,
            editBtn, requestBtn, destroyBtn, saveBtn, acceptBtn, acceptDestroyBtn, addWorkerBtn = new Button();
    @FXML
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7;
    @FXML
    private TextField shelfIDField, boxIDField, clientIDField, periodField, dateFromField, dateEndField, weightField,
            fulfillmentField, statusField, noteField, palRequestField, palDestroyField, searchField = new TextField();
    @FXML
    private Text notificationTxt;
    @FXML
    private Label leftCornerInfoLabel, titleTextLabel, palLabel, enterPalNrLabel, palDestroyLabel,
            enterPalNrDestroyLabel;
    @FXML
    private ImageView myBlackBox, myYellowBox, myBrownBox, myBlueBox, myRedBox, myPinkBox, myGreenBox, myPurpleBox,
            myOrangeBox;
    @FXML
    private ProgressIndicator secretProgressBar;

    @FXML
    private Text bIdForBox1, bIdForBox2, bIdForBox3, bIdForBox4, bIdForBox5, bIdForBox6, bIdForBox7, bIdForBox8, bIdForBox9;

    @FXML
    private ImageView box1, box2, box3, box4, box5, box6, box7, box8, box9;



    List<Pane> allPanels;
    List<ImageView> allBoxIMG;
    List<TextField> textFieldsForConnection;
    List<Button> nrBtns;

    LoadingScreen loadingScreen; //need to move down....test test

    public SampleController() {
        System.out.println("LOADING CONSTRUCTOR");
        System.out.println("LOADING CONSTRUCTOR " + user.getRole());
    }

    @FXML
    public void initialize() {
        //b1Btn.setStyle("-fx-text-fill: BLACK; -fx-background-color: #AAB2BD");
        nrBtns = List.of(btn1, btn2, btn3, btn4, btn5, btn6, btn7);

        textFieldsForConnection = List.of(boxIDField, shelfIDField, clientIDField, periodField, dateFromField,
                dateEndField, weightField, fulfillmentField, statusField, noteField);

        allPanels = List.of(sampleAppPane, searchBoxPane, addWorkerPane, b1Pane, b2Pane, d1Pane, shelfPane, addressPane);
        for (Pane allPanel : allPanels) {
            allPanel.setVisible(false);
        }
        leftCornerInfoLabel.setText("StackApp Choose Destination");
        sampleAppPane.setVisible(true);

        allBoxIMG = List.of(myBlackBox, myBlueBox, myOrangeBox, myBrownBox, myPinkBox, myGreenBox, myPurpleBox, myRedBox,
                myYellowBox);
        for (ImageView imageView : allBoxIMG) {
            imageView.setVisible(false);
        }

        loadingScreen = new SampleController.LoadingScreen(secretProgressBar, myRedBox);
        startAnimationProgress();

        addWorkerBtn.setDisable(true);
        if (user.getRole().equals(ADMIN)) {
            addWorkerBtn.setDisable(false);
        }

        // CHART SETTINGS
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0.0);
        yAxis.setUpperBound(9.0);
        yAxis.setTickUnit(1.0);

        barChart.setAnimated(false);
    }


    /**
     * ----    Default Panel    -----
     */
    @FXML
    private void changePanToDefaultPan() {
        for (Pane allPanel : allPanels) {
            allPanel.setVisible(false);
        }
        leftCornerInfoLabel.setText("StackApp Choose Destination");
        sampleAppPane.setVisible(true);
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
        for (Pane allPanel : allPanels) {
            allPanel.setVisible(false);
        }
        searchBoxPane.setVisible(true);
        leftCornerInfoLabel.setText("StackApp SEARCH BOX");
        destroyBtn.setDisable(true);
        requestBtn.setDisable(true);
        editBtn.setDisable(true);
        saveBtn.setDisable(true);
    }

    @FXML
    private void changePanToSearchBoxPan(String boxID) {
        for (Pane allPanel : allPanels) {
            allPanel.setVisible(false);
        }
        searchBoxPane.setVisible(true);
        leftCornerInfoLabel.setText("StackApp SEARCH BOX");
        destroyBtn.setDisable(true);
        requestBtn.setDisable(true);
        editBtn.setDisable(true);
        saveBtn.setDisable(true);
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

    private void getBoxById(long boxId) {

        TextField[] testFieldsArr = {shelfIDField, clientIDField, periodField, dateFromField, dateEndField,
                weightField, fulfillmentField, statusField, noteField};

        Connect conn = new Connect();
        BoxData box = conn.searchForBox(boxId);
        System.out.println("BoxId is " + boxId);

        if (box == null) {
            System.out.println("it is null");
            for (TextField textField : testFieldsArr) {
                textField.clear();
            }

            boxIDField.setText("" + boxId);
            noteField.setText("No such record! Try again!");
            noteField.setStyle("-fx-text-fill: red; -fx-background-color:  #dce2e8;");

        } else {
            noteField.setStyle("-fx-text-fill: BLACK; -fx-background-color:  #dce2e8");

            boxIDField.setText(valueOf(box.getId()));
            shelfIDField.setText(box.getShelfId());
            clientIDField.setText(valueOf(box.getClient_id()));
            periodField.setText(box.getDateFrom() != null && box.getDateEnd() != null
                    ? calcPeriod(box.getDateFrom(), box.getDateEnd())
                    : EMPTY_STRING);
            dateFromField.setText(box.getDateFrom() != null ? box.getDateFrom() : EMPTY_STRING);
            dateEndField.setText(box.getDateEnd() != null ? box.getDateEnd() : EMPTY_STRING);
            weightField.setText(box.getWeight() != null ? box.getWeight() : EMPTY_STRING);
            fulfillmentField.setText(box.getFulfillment() != null ? box.getFulfillment() : EMPTY_STRING);
            statusField.setText(box.getStatus() != null ? box.getStatus() : EMPTY_STRING);
            noteField.setText(box.getInfoNote() != null ? box.getInfoNote() : EMPTY_STRING);

            //blackpink in the area
            //please stop with blackpink!

        }

    }

    @FXML
    private void getBoxIdByUserInput() {
        TextField[] textFields = {shelfIDField, clientIDField, periodField, dateFromField, dateEndField, weightField,
                fulfillmentField, statusField, noteField};
        //need to add connection to DB to save edits
        for (int i = 0; i < textFields.length; i++) {
            if (shelfIDField.getText().equals("") || clientIDField.getText().equals("") ||
                    noteField.getText().equals("No such record! Try again!")) {
                requestBtn.setDisable(true);
                destroyBtn.setDisable(true);
                saveBtn.setDisable(true);
            } else {
                requestBtn.setDisable(false);
                destroyBtn.setDisable(false);
                saveBtn.setDisable(true);
            }
            acceptBtn.setDisable(true);
            acceptDestroyBtn.setDisable(true);
            saveBtn.setDisable(true);
            editBtn.setDisable(false);
        }
        System.out.println("User enters: [" + boxId + "] BOX_id to search in DB");
        //getFieldInputsFromDB();
        getBoxById(boxId);
        notificationTxt.setStyle("-fx-fill: #aba9a9;");
        notificationTxt.setText("Last search: " + boxId);
    }

    @FXML
    private void onBtnPress_EditBox() {
        TextField[] textFields = {shelfIDField, clientIDField, periodField, dateFromField, dateEndField, weightField,
                fulfillmentField, statusField, noteField};

        for (TextField it : textFields) {
            it.setEditable(true);
        }
        saveBtn.setVisible(true);
        editBtn.setVisible(true);

        destroyBtn.setDisable(true);
        requestBtn.setDisable(true);
        saveBtn.setDisable(false);
        editBtn.setDisable(true);
        notificationTxt.setText("");
        onNoteEditChangeColor();
    }

    @FXML
    private void onBtnPress_DestroyBox() {
        palDestroyField.setVisible(true);
        palDestroyLabel.setVisible(true);
        enterPalNrDestroyLabel.setVisible(true);
        acceptDestroyBtn.setDisable(false);
        editBtn.setDisable(true);
        requestBtn.setDisable(true);
        destroyBtn.setDisable(true);
        saveBtn.setDisable(false);
    }

    @FXML
    private void saveDestroyPalToDB() {

        editBtn.setDisable(false);
        requestBtn.setDisable(false);
        destroyBtn.setDisable(false);
        palDestroyField.setVisible(false);
        palDestroyLabel.setVisible(false);
        enterPalNrDestroyLabel.setVisible(false);
        acceptDestroyBtn.setDisable(true);
        notificationTxt.setStyle("-fx-fill: red;");
        notificationTxt.setText("The box has been successfully deleted and saved in the database at 'PAL[X]:" +
                palDestroyField.getText() + "'");
    }

    @FXML
    private void onBtnPress_RequestBox() {

        palRequestField.setVisible(true);
        palLabel.setVisible(true);
        enterPalNrLabel.setVisible(true);
        acceptBtn.setDisable(false);
        editBtn.setDisable(true);
        requestBtn.setDisable(true);
        destroyBtn.setDisable(true);
        saveBtn.setDisable(false);
    }

    @FXML
    private void saveRequestPalToDB() {

        palRequestField.setVisible(false);
        palLabel.setVisible(false);
        enterPalNrLabel.setVisible(false);

        editBtn.setVisible(true);
        saveBtn.setVisible(false);

        acceptBtn.setDisable(true);
        editBtn.setDisable(false);
        requestBtn.setDisable(false);
        destroyBtn.setDisable(false);
        saveBtn.setDisable(true);
        notificationTxt.setStyle("-fx-fill: #2c6432;");
        notificationTxt.setText(
                "The box has been successfully saved in the database at 'PAL:[R]" +
                palRequestField.getText() + "'");
    }

    @FXML
    private void saveBoxtoDB() {
        TextField[] textFields = {shelfIDField, clientIDField, periodField, dateFromField, dateEndField, weightField,
                fulfillmentField, statusField, noteField};
        //need to add connection to DB to save edits
        String[] testTxtsArr = new String[textFields.length];
        for (int i = 0; i < textFields.length; i++) {
            if (shelfIDField.getText().equals("") || clientIDField.getText().equals("") ||
                    noteField.getText().equals("No such record! Try again!")) {
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

        editBtn.setVisible(true);
        saveBtn.setVisible(false);

        System.out.println("Šī brīža array = " + Arrays.toString(testTxtsArr));
        saveBtn.setDisable(true);
        editBtn.setDisable(false);


    }

    @FXML
    void onNoteEditChangeColor() {
        noteField.setStyle("-fx-text-fill: BLACK; -fx-background-color:  #dce2e8");
    }

    /**    ----END SearchBox Panel END-----    */
//######################################################################################################################


    /**
     * ----    AddWorker Panel    -----
     */
    @FXML
    private void changePanToAddWorkerPan() {
        for (Pane allPanel : allPanels) {
            allPanel.setVisible(false);
        }
        leftCornerInfoLabel.setText("StackApp WORKERS");
        addWorkerPane.setVisible(true);


    }
    /**    ----END AddWorker Panel END-----    */
//######################################################################################################################


    /**
     * ----    B1 Panel    -----
     */
    @FXML
    private void changePanToB1Pan() {
        for (Pane allPanel : allPanels) {
            allPanel.setVisible(false);
        }
        leftCornerInfoLabel.setText("StackApp Column- B1");
        b1Pane.setVisible(true);
    }
    /**    ----END B1 Panel END-----    */
//######################################################################################################################


    /**
     * ----    B1 Panel    -----
     */
    @FXML
    private void changePanToB2Pan() {
        for (Pane allPanel : allPanels) {
            allPanel.setVisible(false);
        }
        leftCornerInfoLabel.setText("StackApp Column- B1");
        b2Pane.setVisible(true);
    }
    /**    ----END B1 Panel END-----    */
//######################################################################################################################


    /**
     * ----    B1 Panel    -----
     */
    @FXML
    private void changePanToD1Pan() {
        for (Pane allPanel : allPanels) {
            allPanel.setVisible(false);
        }
        leftCornerInfoLabel.setText("StackApp Column- B1");
        d1Pane.setVisible(true);

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
     * ----    Address Panel    -----
     */
    @FXML
    private void checksPressedSectionID(ActionEvent event) {
        Button button = (Button) event.getSource();
        String btnText = button.getText();
        String btnID = ((Button) event.getSource()).getId();
        String textToShow= leftCornerInfoLabel.getText().substring(leftCornerInfoLabel.getText().length()-4)+btnID.substring(3);
        System.out.println("Mouse click on label: " + btnText);
        System.out.println("Clicked ID: " + btnID);
        System.out.println("Full ID: "+textToShow);
        changePanToAddressPan(btnID, textToShow);
    }
    @FXML
    void changePanToAddressPan(String btnID, String leftCornerTxt) {
        for (Pane allPanel : allPanels) {
            allPanel.setVisible(false);
        }
        addressPane.setVisible(true);
        System.out.println("Wee are in Section: "+btnID);
        leftCornerInfoLabel.setText("StackApp Section- " + leftCornerTxt);
    }
    /**    ----END Address Panel END-----    */
//######################################################################################################################


    /**
     * ----    Section Panel(Where is all 9Boxes)    -----
     */
    @FXML
    private void checksPressedBoxID(MouseEvent event) {

        //need to connect pressed box wit text on it- BoxID and path it changePanToSearchBoxPan(boxID);


        ImageView img = (ImageView) event.getSource();
        String imgID = ((ImageView) event.getSource()).getId();
        String textToShow= leftCornerInfoLabel.getText().substring(leftCornerInfoLabel.getText().length()-4)+imgID.substring(3);
        System.out.println("Clicked ID: " + imgID);
        System.out.println("Full ID: "+textToShow);

        String boxID= "";
        changePanToSearchBoxPan(boxID);
    }
    /**    ----END Section Panel END-----    */
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
        ImageView[] myBoxImageArr = {myBlackBox, myBlueBox, myOrangeBox, myBrownBox, myPinkBox, myGreenBox, myPurpleBox,
                myRedBox, myYellowBox};
        for (ImageView imageView : myBoxImageArr) {
            imageView.setVisible(false);
        }
        secretProgressBar.setProgress(0);
        startAnimationProgress();
    }

    @FXML
    void stopAnimation() {
        secretProgressBar.setProgress(0);
        running = false;

        ImageView[] myBoxImageArr = {myBlackBox, myBlueBox, myOrangeBox, myBrownBox, myPinkBox, myGreenBox, myPurpleBox,
                myRedBox, myYellowBox};
        for (ImageView imageView : myBoxImageArr) {
            imageView.setVisible(true);
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
        ImageView[] myBoxImageArr = {myBlackBox, myBlueBox, myOrangeBox, myBrownBox, myPinkBox, myGreenBox, myPurpleBox,
                myRedBox, myYellowBox};

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

    /**
     * ----END Animation END-----
     */
//######################################################################################################################
    @FXML
    private void checksPressedShelfID(MouseEvent event) {
        Label label = (Label) event.getSource();
        String labelText = label.getText();
        String labelID = ((Label) event.getSource()).getId();
        System.out.println("Mouse click on label: " + labelText);
        System.out.println("Clicked ID: " + labelID);
        changePanToShelfPan(labelID);
    }

    /**
     * ----    ShelfPanel Panel    -----
     */
    @FXML
    private void changePanToShelfPan(String address) {
        loadGraphWindow(address);
        for (Pane allPanel : allPanels) {
            allPanel.setVisible(false);
        }
        shelfPane.setVisible(true);
        leftCornerInfoLabel.setText("StackApp Shelf- " + address.toUpperCase());
    }

    /**
     * ----END B1 Panel END-----
     */
//######################################################################################################################
    @FXML
    void loadGraphWindow(String address) {
        barChart.setVisible(true);
        barChart.setTitle(address.toUpperCase());

        String temp = address.substring(0, 2);
        int[] xCoordinates = {563, 477, 391, 305, 219, 133, 47};
        int[] xCoordinatesRev = {47, 133, 219, 305, 391, 477, 563};
        if (temp.equals("b1")) {
            for (int i = 0; i < nrBtns.size(); i++) {
                nrBtns.get(i).setLayoutX(xCoordinates[i]);
            }
        } else {
            for (int i = 0; i < nrBtns.size(); i++) {
                nrBtns.get(i).setLayoutX(xCoordinatesRev[i]);
            }
        }

        //insert data from select count
        count1 = 5;
        count2 = 9;
        count3 = 5;
        count4 = 2;
        count5 = 7;
        count6 = 7;
        count7 = 3;

        resetGraph();
        addSerie(new XYChart.Series<>());
    }

    private void resetGraph() {
        if (barChart.getData().size() > 0) {
            barChart.getData().remove(0);
        }
    }

    private void addSerie(XYChart.Series<String, Number> boxSerie) {
        boxSerie.getData().add(new XYChart.Data("A", count1));
        boxSerie.getData().add(new XYChart.Data("B", count2));
        boxSerie.getData().add(new XYChart.Data("C", count3));
        boxSerie.getData().add(new XYChart.Data("D", count4));
        boxSerie.getData().add(new XYChart.Data("E", count5));
        boxSerie.getData().add(new XYChart.Data("F", count6));
        boxSerie.getData().add(new XYChart.Data("G", count7));

        barChart.getData().addAll(boxSerie);
    }
}

