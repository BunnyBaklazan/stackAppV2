package com.example.stackapp;

import javafx.application.Platform;
import javafx.css.Stylesheet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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
        loadingScreen = new LoadingScreen(progressBar, txt);
        startProgress();
    }

    @FXML
    void startProgress() {
        Thread thread = new Thread(loadingScreen);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    void restart() {
        progressBar.setProgress(0);
        startProgress();
    }

    public class LoadingScreen implements Runnable {
        String[] secretTextArr = {"Think inside box", "We need storage", "No box is too big, no customer too small",
                "We have solution", "Let's sort it out!", "Shelf of power", "Boxy of creative"};

        ProgressIndicator progressBar;
        Text txt;

        public LoadingScreen(ProgressIndicator progressIndicator, Text txt) {
            this.progressBar = progressIndicator;
            this.txt = txt;
        }

        @Override
        public void run() {
            while (progressBar.getProgress() <= 1) {
                int[] sequence= {0, 1, 2, 3, 4, 5, 6, 0};
                List<Color> colors= List.of(Color.GREEN, Color.YELLOW, Color.PINK,
                        Color.WHITE, Color.BROWN, Color.PURPLE, Color.BLUE, Color.VIOLET);
                double secondsStart= 0.1;
                double secondsEnd= 0.2;
                for (int i = 0; i < 8; i++) {
                    if (progressBar.getProgress() >= secondsStart && progressBar.getProgress() <= secondsEnd) {
                        txt.setText(secretTextArr[sequence[i]]);
                        txt.setFill(colors.get(i));
                    }
                    secondsStart += 0.1;
                    secondsEnd += 0.1;
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
            txt.setFill(Color.RED);
            myBox.setVisible(true);

        }
    }

    public void openLoginPage() throws IOException {
        if (progressBar.getProgress() >= 0.99) {
            window.changePage(LOGIN_PAGE);
        }
    }
}
