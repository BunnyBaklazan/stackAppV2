package com.example.stackapp.controller;

import static com.example.stackapp.model.SampleUtils.calcPeriod;
import static java.lang.String.valueOf;

import com.example.stackapp.model.BoxData;
import com.example.stackapp.model.UserData;
import connect.net.sqlite.Connect;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.prefs.Preferences;

public class SampleController {
    protected final static Preferences userPreferences = Preferences.userRoot();
    LoadingScreen loadingScreen;

    private static final String BOX_ID_VALIDATOR_MESSAGE = "Box ID must only contains numbers!";
    private static final String ONLY_NUMBERS_REGEX = "[0-9]*";
    private volatile boolean running = true;

    public long boxId;
    public static final String EMPTY_STRING = "";


    //------------------------

    @FXML
    private TextField tf_first_name, tf_last_name, tf_username;

    @FXML
    private PasswordField pass_field;

    @FXML
    private TableView<UserData> table_users;

    @FXML
    private TableColumn<UserData, Integer> tc_id;

    @FXML
    private TableColumn<UserData, String> tc_first_name, tc_last_name, tc_password, tc_username;

    @FXML
    private Label label_error;

    //-------------------------

    @FXML
    private CategoryAxis xAxis = new CategoryAxis();

    @FXML
    private NumberAxis yAxis = new NumberAxis();

    @FXML
    private BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

    @FXML
    private Pane sampleAppPane, b1Pane, b2Pane, d1Pane, searchBoxPane, addWorkerPane, shelfPane, addressPane;

    @FXML
    private Button searchBtn, editBtn, requestBtn, destroyBtn, saveBtn, acceptRequestBtn, acceptDestroyBtn, addWorkerBtn = new Button();

    @FXML
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7;

    @FXML
    private TextField shelfIDField, boxIDField, clientIDField, periodField, dateFromField, dateEndField, weightField,
            fulfillmentField, statusField, noteField, palRequestField, palDestroyField, searchField = new TextField();
    @FXML
    private Text notificationTxt;

    @FXML
    private Label leftCornerInfoLabel, palRequestLabel, enterPalNrRequestLabel, palDestroyLabel,
            enterPalNrDestroyLabel;
    @FXML
    private ImageView myBlackBox, myYellowBox, myBrownBox, myBlueBox, myRedBox, myPinkBox, myGreenBox, myPurpleBox,
            myOrangeBox;
    @FXML
    private ProgressIndicator secretProgressBar;

    @FXML
    private Text bIdForBox1, bIdForBox2, bIdForBox3, bIdForBox4, bIdForBox5, bIdForBox6, bIdForBox7, bIdForBox8, bIdForBox9;

    @FXML
    private Text cIdForBox1, cIdForBox2, cIdForBox3, cIdForBox4, cIdForBox5, cIdForBox6, cIdForBox7, cIdForBox8, cIdForBox9;

    @FXML
    private ImageView box1, box2, box3, box4, box5, box6, box7, box8, box9;

    private int count1, count2, count3, count4, count5, count6, count7;

    private List<ImageView> boxes;
    private List<Text> boxIDs;
    private List<Text> clientIDs;
    private List<Button> nrBtns;
    private List<ImageView> allBoxIMG;
    private List<TextField> textFields;
    private List<TextField> boxTextFields;
    private List<Pane> allPanels;

    @FXML
    public void initialize() {
        boxes = List.of(box1, box2, box3, box4, box5, box6, box7, box8, box9);

        boxIDs = List.of(bIdForBox1, bIdForBox2, bIdForBox3, bIdForBox4,
                bIdForBox5, bIdForBox6, bIdForBox7, bIdForBox8, bIdForBox9);

        clientIDs = List.of(cIdForBox1, cIdForBox2, cIdForBox3, cIdForBox4,
                cIdForBox5, cIdForBox6, cIdForBox7, cIdForBox8, cIdForBox9);

        nrBtns = List.of(btn1, btn2, btn3, btn4, btn5, btn6, btn7);

        allBoxIMG = List.of(myBlackBox, myBlueBox, myOrangeBox, myBrownBox,
                myPinkBox, myGreenBox, myPurpleBox, myRedBox, myYellowBox);

        textFields = List.of(boxIDField, shelfIDField, clientIDField, periodField, dateFromField,
                dateEndField, weightField, fulfillmentField, statusField, noteField);

        boxTextFields = List.of(shelfIDField, clientIDField, periodField, dateFromField,
                dateEndField, weightField, fulfillmentField, statusField, noteField);

        allPanels = List.of(sampleAppPane, searchBoxPane, addWorkerPane,
                b1Pane, b2Pane, d1Pane, shelfPane, addressPane);

        for (ImageView imageView : allBoxIMG) {
            imageView.setVisible(false);
        }

        loadingScreen = new SampleController.LoadingScreen(secretProgressBar, myRedBox);
        startAnimationProgress();
        changePanToDefaultPan();

        // CHART SETTINGS
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0.0);
        yAxis.setUpperBound(10.0);
        yAxis.setTickUnit(1.0);
        barChart.setAnimated(false);

        // ADMIN PAGE
        String role = userPreferences.get("role", null);
        if (role.equals("admin")) { // need to change to "admin"
            System.out.println("admin is on");
            System.out.println("You are in the admin dashboard");
            addWorkerBtn.setVisible(true);
            showUsersTable();
        } else {
            System.out.println("no admin");
            addWorkerBtn.setVisible(false);
            // show content from sample controller
        }
    }
//######################################################################################################################

    /**
     * ----    boolean paradise    -----
     */
    @FXML
    private void setAllPaletFalse() {
        palRequestField.setVisible(false);
        palRequestLabel.setVisible(false);
        enterPalNrRequestLabel.setVisible(false);

        palDestroyField.setVisible(false);
        palDestroyLabel.setVisible(false);
        enterPalNrDestroyLabel.setVisible(false);
    }

    /**    ----END boolean paradise END-----    */
//######################################################################################################################

    /**
     * ----    Default Panel    -----
     */
    @FXML
    private void changePanToDefaultPan() {
        for (TextField textField : textFields) {
            textField.setText("");
            textField.setEditable(false);
        }
        for (Pane allPanel : allPanels) {
            allPanel.setVisible(false);
        }
        sampleAppPane.setVisible(true);
        leftCornerInfoLabel.setText("StackApp Choose Destination");

        destroyBtn.setDisable(true);
        requestBtn.setDisable(true);
        editBtn.setDisable(true);
        editBtn.setVisible(true);
        searchBtn.setDisable(false);
        saveBtn.setVisible(false);
        acceptRequestBtn.setVisible(false);
        acceptDestroyBtn.setVisible(false);
        setAllPaletFalse();
        searchField.setText("");
        restartAnimation();
    }

    /**    ----END Default Panel END-----    */
//######################################################################################################################

    /**
     * ----    SearchBox Panel    -----
     */
    @FXML
    private void changePanToSearchBoxPan() {
        changePanToDefaultPan();
        searchBoxPane.setVisible(true);
        leftCornerInfoLabel.setText("StackApp SEARCH BOX");
    }

    @FXML
    private void changePanToSearchBoxPan(String boxID) throws SQLException {
        changePanToSearchBoxPan();
        searchField.setText(boxID);
        getBoxById(Long.parseLong(boxID));
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
            boxId = Integer.parseInt(userInput);
        }
    }

    private void getBoxById(long boxId) throws SQLException {
        BoxData box = Connect.searchForBox(boxId);
        System.out.println("BoxId is " + boxId);

        if (box == null) {
            for (TextField textField : boxTextFields) {
                textField.clear();
            }
            boxIDField.setText("" + boxId);
            noteField.setText("No such record! Try again!");
            noteField.setStyle("-fx-text-fill: red; -fx-background-color:  #dce2e8;");

        } else {

            noteField.setText("");
            noteField.setStyle("-fx-text-fill: BLACK; -fx-background-color:  #dce2e8");
            boxIDField.setText(valueOf(box.getId()));
            shelfIDField.setText(box.getShelfId());
            clientIDField.setText(valueOf(box.getClientId()));
            dateFromField.setText(box.getDateFrom() != null ? box.getDateFrom() : EMPTY_STRING);
            dateEndField.setText(box.getDateEnd() != null ? box.getDateEnd() : EMPTY_STRING);
            weightField.setText(box.getWeight() != null ? box.getWeight() : EMPTY_STRING);
            fulfillmentField.setText(box.getFulfillment() != null ? box.getFulfillment() : EMPTY_STRING);
            statusField.setText(box.getStatus() != null ? box.getStatus() : EMPTY_STRING);
            noteField.setText(box.getInfoNote() != null ? box.getInfoNote() : EMPTY_STRING);

            //troublesome part of code begins:
            periodField.setText(box.getDateFrom() != null && box.getDateEnd() != EMPTY_STRING
                    ? calcPeriod(box.getDateFrom(), box.getDateEnd())
                    : null);
        }
    }

    @FXML
    private void getBoxIdByUserInput() throws SQLException {
        for (TextField boxTextField : boxTextFields) {
            boxTextField.setEditable(false);
            boxTextField.setText("");
        }

        setAllPaletFalse();

        saveBtn.setVisible(false);
        acceptRequestBtn.setVisible(false);
        acceptDestroyBtn.setVisible(false);
        editBtn.setVisible(true);
        editBtn.setDisable(false);
        getBoxById(boxId);

        for (int i = 0; i < boxTextFields.size(); i++) {
            if (shelfIDField.getText().equals("") || clientIDField.getText().equals("")) {
                requestBtn.setDisable(true);
                destroyBtn.setDisable(true);
            } else {
                requestBtn.setDisable(false);
                destroyBtn.setDisable(false);
            }
        }
        notificationTxt.setStyle("-fx-fill: #aba9a9;");
        notificationTxt.setText("Last search: " + boxId);
    }

    @FXML
    private void onBtnPress_EditBox() {
        if (noteField.getText().equals("No such record! Try again!")) {
            noteField.setText("");
        }
        for (TextField tf : boxTextFields) {
            tf.setEditable(true);
        }
        saveBtn.setVisible(true);
        editBtn.setVisible(false);
        destroyBtn.setDisable(true);
        requestBtn.setDisable(true);
        notificationTxt.setText("");
        onNoteEditChangeColor();
    }

    private void shortcutForReqAndDes(boolean reverse) {
        if(!reverse) {
            editBtn.setDisable(true);
            editBtn.setVisible(false);
            requestBtn.setDisable(true);
            destroyBtn.setDisable(true);
            saveBtn.setVisible(true);
        } else {
            editBtn.setDisable(false);
            editBtn.setVisible(true);
            requestBtn.setDisable(false);
            destroyBtn.setDisable(false);
            saveBtn.setVisible(false);
        }

    }

    @FXML
    private void onBtnPress_RequestBox() {
        palRequestField.setVisible(true);
        palRequestLabel.setVisible(true);
        enterPalNrRequestLabel.setVisible(true);
        acceptRequestBtn.setVisible(true);
        shortcutForReqAndDes(false);
    }

    @FXML
    private void saveRequestPalToDB() {
        if (palRequestField.getText().isEmpty()) {
            notificationTxt.setText("Add PAL[R]:nr!");
            notificationTxt.setStyle("-fx-fill: red;");

        } else {

            Connect.updateShelfId(
                    "PALR" + palRequestField.getText(),
                    Long.parseLong(boxIDField.getText())
            );

            palRequestField.setVisible(false);
            palRequestLabel.setVisible(false);
            enterPalNrRequestLabel.setVisible(false);

            acceptRequestBtn.setVisible(false);
            shortcutForReqAndDes(true);

            notificationTxt.setStyle("-fx-fill: #2c6432;");
            notificationTxt.setText(
                    "The box has been successfully saved in the database at 'PAL[R]:" +
                            palRequestField.getText() + "'");

        }
    }

    @FXML
    private void onBtnPress_DestroyBox() {
        palDestroyField.setVisible(true);
        palDestroyLabel.setVisible(true);
        enterPalNrDestroyLabel.setVisible(true);
        acceptDestroyBtn.setVisible(true);
        shortcutForReqAndDes(false);
    }

    @FXML
    private void saveDestroyPalToDB() {
        if (palDestroyField.getText().equals("")) {
            notificationTxt.setText("Add PAL[X]:nr!");
            notificationTxt.setStyle("-fx-fill: red;");

        } else {

            Connect.updateShelfId(
                    "PALX" + palDestroyField.getText(),
                    Long.parseLong(boxIDField.getText())
            );

            palDestroyField.setVisible(false);
            palDestroyLabel.setVisible(false);
            enterPalNrDestroyLabel.setVisible(false);
            acceptDestroyBtn.setVisible(false);
            shortcutForReqAndDes(true);

            notificationTxt.setStyle("-fx-fill: #2c6432;");
            notificationTxt.setText(
                    "The box has been successfully deleted and saved in the database at 'PAL[X]:" +
                            palDestroyField.getText() + "'");
        }
    }

    @FXML
    private void saveBox() {
        if (shelfIDField.getText().isEmpty() || clientIDField.getText().isEmpty()
                || boxIDField.getText().isEmpty()) {

            notificationTxt.setStyle("-fx-fill: red;");
            notificationTxt.setText("Error, adding Box to the database. Please fill ShelfID and ClientID");
            editBtn.setVisible(false);
            saveBtn.setVisible(true);

        } else {
            Connect.insertBox(new BoxData(
                    Long.parseLong(boxIDField.getText()),
                    Long.parseLong(clientIDField.getText()),
                    dateFromField.getText() != null ? dateFromField.getText() : EMPTY_STRING,
                    dateEndField.getText() != null ? dateEndField.getText() : EMPTY_STRING,
                    fulfillmentField.getText() != null ? fulfillmentField.getText() : EMPTY_STRING,
                    statusField.getText() != null ? statusField.getText() : EMPTY_STRING,
                    noteField.getText() != null ? noteField.getText() : EMPTY_STRING,
                    weightField.getText() != null ? weightField.getText() : EMPTY_STRING,
                    shelfIDField.getText()
            ));
            notificationTxt.setStyle("-fx-fill: #2c6432;");
            notificationTxt.setText("The box has been successfully saved in the database");
            editBtn.setVisible(true);
            saveBtn.setVisible(false);
        }
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
        changePanToDefaultPan();
        addWorkerPane.setVisible(true);
        leftCornerInfoLabel.setText("StackApp WORKERS");
    }
    /**    ----END AddWorker Panel END-----    */
//######################################################################################################################


    /**
     * ----    B1 Panel    -----
     */
    @FXML
    private void changePanToB1Pan() {
        changePanToDefaultPan();
        b1Pane.setVisible(true);
        leftCornerInfoLabel.setText("StackApp Column- B1");

    }
    /**    ----END B1 Panel END-----    */
//######################################################################################################################


    /**
     * ----    B2 Panel    -----
     */
    @FXML
    private void changePanToB2Pan() {
        changePanToDefaultPan();
        b2Pane.setVisible(true);
        leftCornerInfoLabel.setText("StackApp Column- B2");
    }
    /**    ----END B2 Panel END-----    */
//######################################################################################################################


    /**
     * ----    D1 Panel    -----
     */
    @FXML
    private void changePanToD1Pan() {
        changePanToDefaultPan();
        d1Pane.setVisible(true);
        leftCornerInfoLabel.setText("StackApp Column- D1");
    }
    /**    ----END D1 Panel END-----    */
//######################################################################################################################


    /**
     * ----    Shelf Panel    -----
     */
    @FXML
    private void checksPressedShelfID(MouseEvent event) {
        String labelID = ((Label) event.getSource()).getId().toUpperCase();
        changePanToShelfPan(labelID);
    }

    @FXML
    private void changePanToShelfPan(String address) {
        loadGraphWindow(address);
        for (Pane allPanel : allPanels) {
            allPanel.setVisible(false);
        }
        shelfPane.setVisible(true);
        leftCornerInfoLabel.setText("StackApp Shelf- " + address);
    }

    /**
     * ----END Shelf Panel END-----
     */
//######################################################################################################################

    /**
     * ----    Address Panel    -----
     */
    @FXML
    private void checksPressedSectionID(ActionEvent event) {
        Button button = (Button) event.getSource();
        String btnText = button.getText();
        String btnID = ((Button) event.getSource()).getId();
        String textToShow = leftCornerInfoLabel.getText()
                .substring(leftCornerInfoLabel.getText().length() - 4) + btnID.substring(3);
        System.out.println("Mouse click on Button: " + btnText);
        System.out.println("Clicked Btn ID: " + btnID);
        System.out.println("Full ID: " + textToShow);

        Connect conn = new Connect();
        int boxesAtGraph = conn.capacityOf(textToShow);

        changePanToAddressPan(textToShow, boxesAtGraph);
    }

    @FXML
    void changePanToAddressPan(String leftCornerTxtPart, int boxesAtGraph) {
        for (Pane allPanel : allPanels) {
            allPanel.setVisible(false);
        }
        addressPane.setVisible(true);

        leftCornerInfoLabel.setText("StackApp Section- " + leftCornerTxtPart);

        // refactor for real data from DB
        List<String> boxIDss = List.of("8322", "56007", "12202", "23300", "21220", "1200", "55200", "27820", "182");
        List<String> clientIDss = List.of("5", "57", "22", "23", "2", "12", "5", "20", "5");


        for (int i = 0; i < boxes.size(); i++) {
            boxes.get(i).setVisible(false);
            clientIDs.get(i).setVisible(false);
            boxIDs.get(i).setVisible(false);
        }

        for (int i = 1; i <= boxes.size(); i++) {
            if (boxesAtGraph == i) {
                for (int j = 0; j < boxes.size(); j++) {
                    if (j < boxesAtGraph) {
                        boxes.get(j).setVisible(true);
                        clientIDs.get(j).setVisible(true);
                        clientIDs.get(j).setText(clientIDss.get(j)); // refactor for real data from DB
                        boxIDs.get(j).setVisible(true);
                        boxIDs.get(j).setText(boxIDss.get(j)); // refactor for real data from DB
                    }
                }
            }
        }

        // and also numbers with them with real boxes...


    }
    /**    ----END Address Panel END-----    */
//######################################################################################################################

    /**
     * ----    Section Panel(Where is all 9Boxes)    -----
     */
    @FXML
    private void checksPressedBoxID(MouseEvent event) throws SQLException {
        String boxID = "";

        //need to connect pressed box with text on it- BoxID and path it changePanToSearchBoxPan(boxID);

        List<String> boxesStr = List.of("box1", "box2", "box3", "box4", "box5", "box6", "box7", "box8", "box9");

        String imgID = ((ImageView) event.getSource()).getId();
        System.out.println("Clicked Box ID: " + imgID);

        for (int i = 0; i < boxesStr.size(); i++) {
            if (imgID.equals(boxesStr.get(i))) {
                boxID = boxIDs.get(i).getText();
            }
        }


        changePanToSearchBoxPan(boxID);
    }
    /**    ----END Section Panel END-----    */
//######################################################################################################################

    /**
     * ----    Graph window    -----
     */
    @FXML
    void loadGraphWindow(String address) {

        boolean mirrorShelf;
        barChart.setVisible(true);
        barChart.setTitle(address);
        String temp = address.substring(0, 2).toUpperCase();
        int[] xCoordinates = {563, 477, 391, 305, 219, 133, 47};
        int[] xCoordinatesRev = {47, 133, 219, 305, 391, 477, 563};
        if (temp.equals("B1")) { //for other D's need add OR(||)
            mirrorShelf = true;
            for (int i = 0; i < nrBtns.size(); i++) {
                nrBtns.get(i).setLayoutX(xCoordinates[i]);
            }
        } else {
            mirrorShelf = false;
            for (int i = 0; i < nrBtns.size(); i++) {
                nrBtns.get(i).setLayoutX(xCoordinatesRev[i]);
            }
        }
        Connect conn = new Connect();


        // This shortcut doesnt work!!!
        /*int[] counts = {count1, count2, count3, count4, count5, count6, count7};
        for (int i = 0; i < counts.length; i++) {
            counts[i] = conn.capacityOf(address + (i+1));
            System.out.println(address + (i+1));
        }*/
        //insert data from select count
        count1 = conn.capacityOf(address + "1");
        count2 = conn.capacityOf(address + "2");
        count3 = conn.capacityOf(address + "3");
        count4 = conn.capacityOf(address + "4");
        count5 = conn.capacityOf(address + "5");
        count6 = conn.capacityOf(address + "6");
        count7 = conn.capacityOf(address + "7");

        System.out.println(count1 + " " + count2 + " " + count3 + " " + count4 + " " + count5 + " " + count6 + " " + count7);

        resetGraph();
        addSerie(new XYChart.Series<>(), mirrorShelf);
    }

    private void resetGraph() {
        if (barChart.getData().size() > 0) {
            barChart.getData().remove(0);
        }
    }

    private void addSerie(XYChart.Series<String, Number> boxSerie, boolean mirrorShelf) {
        if (!mirrorShelf) {
            boxSerie.getData().add(new XYChart.Data("A", count1));
            boxSerie.getData().add(new XYChart.Data("B", count2));
            boxSerie.getData().add(new XYChart.Data("C", count3));
            boxSerie.getData().add(new XYChart.Data("D", count4));
            boxSerie.getData().add(new XYChart.Data("E", count5));
            boxSerie.getData().add(new XYChart.Data("F", count6));
            boxSerie.getData().add(new XYChart.Data("G", count7));
            barChart.getData().addAll(boxSerie);
        } else {
            boxSerie.getData().add(new XYChart.Data("A", count7));
            boxSerie.getData().add(new XYChart.Data("B", count6));
            boxSerie.getData().add(new XYChart.Data("C", count5));
            boxSerie.getData().add(new XYChart.Data("D", count4));
            boxSerie.getData().add(new XYChart.Data("E", count3));
            boxSerie.getData().add(new XYChart.Data("F", count2));
            boxSerie.getData().add(new XYChart.Data("G", count1));
            barChart.getData().addAll(boxSerie);
        }
    }

    /**
     * ----    END Graph window    -----
     */
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

        List<ImageView> myBoxImageArr = List.of(myBlackBox, myBlueBox, myOrangeBox,
                myBrownBox, myPinkBox, myGreenBox, myPurpleBox, myRedBox, myYellowBox);
        int[] xCoordinates = {22, 354, 263, 421, 188, 496, 105, 338, 180};
        int[] yCoordinates = {495, 248, 372, 372, 248, 495, 372, 495, 495};

        for (ImageView imageView : myBoxImageArr) {
            imageView.setVisible(true);
        }

        for (int i = 0; i < myBoxImageArr.size(); i++) {
            myBoxImageArr.get(i).setLayoutX(xCoordinates[i]);
            myBoxImageArr.get(i).setLayoutY(yCoordinates[i]);
            myBoxImageArr.get(i).setRotate(0);
        }
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
                int[] boxNr = {7, 0, 3, 1, 6, 4, 2, 5, 8};
                int[] xCoordinates = {188, 354, 105, 263, 421, 22, 180, 338, 496};
                int[] yCoordinates = {248, 248, 372, 372, 372, 495, 495, 495, 495};

                double secondsStart = 0.10;
                double secondsEnd = 0.15;
                for (int j : boxNr) {
                    if (secretProgressBar.getProgress() >= secondsStart && secretProgressBar.getProgress() <= secondsEnd) {
                        myBoxImageArr[j].setVisible(true);
                    }
                    secondsStart += 0.05;
                    secondsEnd += 0.05;
                }

                secondsStart = 0.55;
                secondsEnd = 0.6;
                for (int i = 0; i < boxNr.length; i++) {
                    if (secretProgressBar.getProgress() >= secondsStart && secretProgressBar.getProgress() <= secondsEnd) {
                        myBoxImageArr[boxNr[i]].setLayoutX(xCoordinates[i]);
                        myBoxImageArr[boxNr[i]].setLayoutY(yCoordinates[i]);
                    }
                    secondsStart += 0.05;
                    secondsEnd += 0.05;
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

    /**
     * ----    Admin Page    -----
     */

    @FXML
    private void handleMouseAction() {
        UserData user = table_users.getSelectionModel().getSelectedItem();
        if (user != null) {
            tf_first_name.setText(user.getFirstName());
            tf_last_name.setText(user.getLastName());
            pass_field.setText(user.getPassword());
            tf_username.setText(user.getUserName());
        }
    }

    private boolean isValidUserData() {
        String firstName = tf_first_name.getText().trim();
        String lastName = tf_last_name.getText().trim();
        String password = pass_field.getText().trim();
        String username = tf_username.getText().trim();
        return !firstName.equals("") && !lastName.equals("") && !password.equals("") && !username.equals("");
    }

    private void setEmptyStrings() {
        tf_first_name.setText("");
        tf_last_name.setText("");
        pass_field.setText("");
        tf_username.setText("");
    }

    @FXML
    private void delete() {
        UserData user = table_users.getSelectionModel().getSelectedItem();
        Connect.deleteUser(user.getId());
        setEmptyStrings();
        showUsersTable();
    }

    @FXML
    private void update() {
        Connect.updateUserTable(
                new UserData(
                        tf_first_name.getText(),
                        tf_last_name.getText(),
                        pass_field.getText(),
                        tc_id.getText()
                )
        );

        setEmptyStrings();
        showUsersTable();
    }

    private String encryptPass(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @FXML
    private void checkIfErrorIsOn() {
        label_error.setText("");
    }

    @FXML
    private void insert() {
        UserData user = new UserData(
                tf_first_name.getText(),
                tf_last_name.getText(),
                encryptPass(pass_field.getText()),
                tf_username.getText());

        if (!isValidUserData()) {
            System.out.println("Invalid Credentials");
            label_error.setText("Error! Enter all text fields!");
        } else {
            System.out.println("Inserting user");
            label_error.setText("");
            Connect.insertUser(user);
            setEmptyStrings();
            showUsersTable(); // show table after insertion
        }
    }


    public ObservableList<UserData> getUsersData() {
        ObservableList<UserData> usersList = FXCollections.observableArrayList();

        //Connect conn = new Connect();
        ResultSet result = Connect.showAllUsers();

        try {
            while (result.next()) {
                UserData user = new UserData(
                        result.getInt("id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("password"),
                        result.getString("username")
                );

                usersList.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usersList;
    }

    public void showUsersTable() {
        ObservableList<UserData> list = getUsersData();

        tc_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tc_first_name.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tc_last_name.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tc_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        tc_username.setCellValueFactory(new PropertyValueFactory<>("userName"));

        table_users.setItems(list);
    }

    @FXML
    void setUsername() {
        String name = tf_first_name.getText().toLowerCase();
        String surname = tf_last_name.getText().toLowerCase();
        tf_username.setText(name.toLowerCase() + "." + surname.toLowerCase());
    }

    /**
     * ----    END Admin Page    -----
     */
//######################################################################################################################
}

