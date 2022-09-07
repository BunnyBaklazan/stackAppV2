package com.example.stackapp.controller;

import com.example.stackapp.model.BoxData;
import com.example.stackapp.model.User;
import connect.net.sqlite.Connect;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.Arrays;

import static com.example.stackapp.model.SampleUtils.calcPeriod;

public class SampleController {
    private volatile boolean running = true;
    
    long boxId = 1;
    private final String ADMIN = "admin";
    private User user = new User("asd", "admin");
    @FXML
    private Pane sampleAppPane, searchBoxPane, addWorkerPane;
    @FXML
    private Button b1, b2, d1, d2, d3, d4, d5, d6, d7, d8, showSearchBox_Btn, addWorkerBtn = new Button(), editBtn, requestBtn, destroyBtn, saveBtn, acceptBtn, acceptDestroyBtn;
    @FXML
    private TextField searchField = new TextField(), shelf_IDField, box_IDField, client_IDField, periodField, date_fromField, date_endField, weightField, fulfillmentField, statusField, noteField, palField, palDestroyField;
    @FXML
    private Text notificationTxt;
    @FXML
    private Label titleTextLabel, palLabel, enterPalNrLabel, palDestroyLabel, enterPalNrDestroyLabel;
    @FXML
    private ImageView myBlackBox, myYellowBox, myBrownBox, myBlueBox, myRedBox, myPinkBox, myGreenBox, myPurpleBox, myOrangeBox;
    @FXML
    private ProgressIndicator secretProgressBar;

    LoadingScreen loadingScreen; //need to move down....test test


    public SampleController () {
        System.out.println("LOADING CONSTRUCTOR");
        System.out.println("LOADING CONSTRUCTOR " + user.getRole());
    }
    @FXML
    public void initialize() {
        ImageView[] myBoxImageArr= {myBlackBox, myBlueBox, myOrangeBox, myBrownBox, myPinkBox, myGreenBox, myPurpleBox, myRedBox, myYellowBox};
        for (int i = 0; i < myBoxImageArr.length; i++) {
            myBoxImageArr[i].setVisible(false);
        }
        loadingScreen= new SampleController.LoadingScreen(secretProgressBar, myRedBox);
        startAnimationProgress();

        addWorkerBtn.setVisible(false);
        if (user.getRole().equals(ADMIN)) {
            System.out.println("INSIDE");
            addWorkerBtn.setVisible(true);
        }
        System.out.println(addWorkerBtn.isVisible());

    }


    /**    ----    Default Panel    -----    */
    @FXML
    private void changePanToDefaultPan() {
        sampleAppPane.setVisible(true);
        searchBoxPane.setVisible(false);
        addWorkerPane.setVisible(false);
        restartAnimation();
    }

    @FXML
    private void checkBtn() {
        System.out.println("Btn pressed");
    }
    /**    ----END Default Panel END-----    */
//######################################################################################################################


    /**    ----    SearchBox Panel    -----    */
    @FXML
    private void changePanToSearchBoxPan() {
        sampleAppPane.setVisible(false);
        searchBoxPane.setVisible(true);
        addWorkerPane.setVisible(false);
    }
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
        TextField[] textFields = {shelf_IDField, client_IDField, periodField, date_fromField, date_endField, weightField, fulfillmentField, statusField, noteField};
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
        getBoxById();
        notificationTxt.setStyle("-fx-fill: #aba9a9;");
        notificationTxt.setText("Last search: "+boxId);
    }


    private void getBoxById() {

        Connect conn = new Connect();
        BoxData box = conn.searchForBox(boxId);

        if (box == null) {
            System.out.println("We don't have that box");
            noteField.setText("No such record! Try again!");
            noteField.setStyle("-fx-text-fill: red; -fx-background-color:  #dce2e8;");

        } else {
            //refactor later
            shelf_IDField.setText(box.getShelfId());
            box_IDField.setText(Long.toString(boxId));
            shelf_IDField.setText(box.getShelfId());
            client_IDField.setText(Long.toString(box.getClient_id()));
            periodField.setText(calcPeriod(box.getDate_from(), box.getDate_end()));
            date_fromField.setText(box.getDate_from());
            date_endField.setText(box.getDate_end());
            weightField.setText(box.getWeight());
            fulfillmentField.setText(box.getFulfillment());
            statusField.setText(box.getStatus());
            noteField.setText(box.getInfo_note());
        }

    }
    /**    ----END SearchBox Panel END-----    */
//######################################################################################################################


    /**    ----    AddWorker Panel    -----    */
    @FXML
    private void changePanToAddWorkerPan() {
        sampleAppPane.setVisible(false);
        searchBoxPane.setVisible(false);
        addWorkerPane.setVisible(true);
    }
    /**    ----END AddWorker Panel END-----    */
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

    /**    ----    Animation    -----    */

    @FXML
    void startAnimationProgress() {
        Thread thread= new Thread(loadingScreen);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    void restartAnimation() {
        running= true;
        ImageView[] myBoxImageArr= {myBlackBox, myBlueBox, myOrangeBox, myBrownBox, myPinkBox, myGreenBox, myPurpleBox, myRedBox, myYellowBox};
        for (int i = 0; i < myBoxImageArr.length; i++) {
            myBoxImageArr[i].setVisible(false);
        }
        secretProgressBar.setProgress(0);
        startAnimationProgress();
    }

    @FXML
    void stopAnimation() {
        secretProgressBar.setProgress(0);
        running= false;

        ImageView[] myBoxImageArr= {myBlackBox, myBlueBox, myOrangeBox, myBrownBox, myPinkBox, myGreenBox, myPurpleBox, myRedBox, myYellowBox};
        for (int i = 0; i < myBoxImageArr.length; i++) {
            myBoxImageArr[i].setVisible(true);
        }

        myPinkBox.setLayoutX(188); myPinkBox.setLayoutY(248); myPinkBox.setRotate(0);
        myBlueBox.setLayoutX(354); myBlueBox.setLayoutY(248); myBlueBox.setRotate(0);

        myPurpleBox.setLayoutX(105); myPurpleBox.setLayoutY(372); myPurpleBox.setRotate(0);
        myOrangeBox.setLayoutX(263); myOrangeBox.setLayoutY(372); myOrangeBox.setRotate(0);
        myBrownBox.setLayoutX(421); myBrownBox.setLayoutY(372); myBrownBox.setRotate(0);

        myBlackBox.setLayoutX(22);myBlackBox.setLayoutY(495); myBlackBox.setRotate(0);
        myYellowBox.setLayoutX(180); myYellowBox.setLayoutY(495); myYellowBox.setRotate(0);
        myRedBox.setLayoutX(338); myRedBox.setLayoutY(495); myRedBox.setRotate(0);
        myGreenBox.setLayoutX(496); myGreenBox.setLayoutY(495); myGreenBox.setRotate(0);




    }
    public class LoadingScreen implements Runnable {
        ImageView[] myBoxImageArr= {myBlackBox, myBlueBox, myOrangeBox, myBrownBox, myPinkBox, myGreenBox, myPurpleBox, myRedBox, myYellowBox};

        ProgressIndicator secretProgressBar;
        ImageView img;

        public LoadingScreen(ProgressIndicator secretProgressBar, ImageView img){
            this.secretProgressBar = secretProgressBar;
            this.img= img;
        }

        @Override
        public void run() {
            while (secretProgressBar.getProgress() <= 1 && running) {
                if(secretProgressBar.getProgress()>= 0.1 && secretProgressBar.getProgress()<= 0.15) {
                    myBoxImageArr[7].setVisible(true);
                } else if(secretProgressBar.getProgress()>= 0.15 && secretProgressBar.getProgress()<= 0.2){
                    myBoxImageArr[0].setVisible(true);
                } else if(secretProgressBar.getProgress()>= 0.2 && secretProgressBar.getProgress()<= 0.25){
                    myBoxImageArr[3].setVisible(true);
                } else if(secretProgressBar.getProgress()>= 0.25 && secretProgressBar.getProgress()<= 0.3){
                    myBoxImageArr[1].setVisible(true);
                } else if(secretProgressBar.getProgress()>= 0.3 && secretProgressBar.getProgress()<= 0.35){
                    myBoxImageArr[6].setVisible(true);
                } else if(secretProgressBar.getProgress()>= 0.35 && secretProgressBar.getProgress()<= 0.4){
                    myBoxImageArr[4].setVisible(true);
                } else if(secretProgressBar.getProgress()>= 0.4 && secretProgressBar.getProgress()<= 0.45){
                    myBoxImageArr[2].setVisible(true);
                } else if(secretProgressBar.getProgress()>= 0.45 && secretProgressBar.getProgress()<= 0.5){
                    myBoxImageArr[5].setVisible(true);
                } else if(secretProgressBar.getProgress()>= 0.5 && secretProgressBar.getProgress()<= 0.55){
                    myBoxImageArr[8].setVisible(true);
                } else if(secretProgressBar.getProgress()>= 0.55 && secretProgressBar.getProgress()<= 0.6){
                    myBoxImageArr[7].setLayoutX(188); myBoxImageArr[7].setLayoutY(248);
                } else if(secretProgressBar.getProgress()>= 0.6 && secretProgressBar.getProgress()<= 0.65){
                    myBoxImageArr[0].setLayoutX(354); myBoxImageArr[0].setLayoutY(248);
                } else if(secretProgressBar.getProgress()>= 0.65 && secretProgressBar.getProgress()<= 0.7){
                    myBoxImageArr[3].setLayoutX(105); myBoxImageArr[3].setLayoutY(372);
                } else if(secretProgressBar.getProgress()>= 0.7 && secretProgressBar.getProgress()<= 0.75){
                    myBoxImageArr[1].setLayoutX(263); myBoxImageArr[1].setLayoutY(372);
                } else if(secretProgressBar.getProgress()>= 0.75 && secretProgressBar.getProgress()<= 0.8){
                    myBoxImageArr[6].setLayoutX(421); myBoxImageArr[6].setLayoutY(372);
                } else if(secretProgressBar.getProgress()>= 0.8 && secretProgressBar.getProgress()<= 0.85){
                    myBoxImageArr[4].setLayoutX(22); myBoxImageArr[4].setLayoutY(495);
                } else if(secretProgressBar.getProgress()>= 0.85 && secretProgressBar.getProgress()<= 0.9){
                    myBoxImageArr[2].setLayoutX(180); myBoxImageArr[2].setLayoutY(495);
                } else if(secretProgressBar.getProgress()>= 0.9 && secretProgressBar.getProgress()<= 0.95){
                    myBoxImageArr[5].setLayoutX(338); myBoxImageArr[5].setLayoutY(495);
                } else if(secretProgressBar.getProgress()>= 0.95 && secretProgressBar.getProgress()<= 0.99){
                    myBoxImageArr[8].setLayoutX(496); myBoxImageArr[8].setLayoutY(495);
                }
                Platform.runLater(() -> secretProgressBar.setProgress(secretProgressBar.getProgress() + 0.01));
                synchronized (this) {
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        running= false;
                    }
                }
            }

        }
    }
    /**    ----END Animation END-----    */
//######################################################################################################################
}
