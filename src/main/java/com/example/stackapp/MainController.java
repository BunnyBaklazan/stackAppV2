package com.example.stackapp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private static final String LOGIN_PAGE = "pages/log-in.fxml";
    private static final Main window = new Main();

    @FXML
    private ProgressIndicator progressBar;
    @FXML
    private Text txt;
    @FXML
    private ImageView myBox;

    LoadingScreen loadingScreen;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadingScreen= new LoadingScreen(progressBar, txt);
        startProgress();
    }

    @FXML
    void startProgress() {
        Thread thread= new Thread(loadingScreen);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    void restart() {
        progressBar.setProgress(0);
        startProgress();
    }

    public class LoadingScreen implements Runnable {
        String[] secretTextArr = {"Think inside box", "We need storage", "No box is too big, no customer too small", "We have solution", "Let's sort it out!", "Shelf of power", "Boxy of creative"};

        ProgressIndicator progressBar;
        Text txt;

        public LoadingScreen(ProgressIndicator progressIndicator, Text txt){
            this.progressBar= progressIndicator;
            this.txt= txt;
        }

        @Override
        public void run() {
            while (progressBar.getProgress() <= 1) {
                if(progressBar.getProgress()>= 0.1 && progressBar.getProgress()<= 0.2) {
                    txt.setText(secretTextArr[0]);
                } else if(progressBar.getProgress()>= 0.2 && progressBar.getProgress()<= 0.3){
                    txt.setText(secretTextArr[1]);
                } else if(progressBar.getProgress()>= 0.3 && progressBar.getProgress()<= 0.4){
                    txt.setText(secretTextArr[2]);
                } else if(progressBar.getProgress()>= 0.4 && progressBar.getProgress()<= 0.5){
                    txt.setText(secretTextArr[3]);
                } else if(progressBar.getProgress()>= 0.5 && progressBar.getProgress()<= 0.6){
                    txt.setText(secretTextArr[4]);
                } else if(progressBar.getProgress()>= 0.6 && progressBar.getProgress()<= 0.7){
                    txt.setText(secretTextArr[5]);
                } else if(progressBar.getProgress()>= 0.7 && progressBar.getProgress()<= 0.8){
                    txt.setText(secretTextArr[6]);
                } else if(progressBar.getProgress()>= 0.8 && progressBar.getProgress()<= 0.9){
                    txt.setText(secretTextArr[0]);
                }
                Platform.runLater(() -> progressBar.setProgress(progressBar.getProgress() + 0.01));
                synchronized (this) {
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            txt.setText("STORAGE SOLUTIONS");
            myBox.setVisible(true);

        }
    }

    public void openLoginPage() throws IOException {
        if(progressBar.getProgress()>= 0.99) {
            window.changePage(LOGIN_PAGE);
        }
    }
}
