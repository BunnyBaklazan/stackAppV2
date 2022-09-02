package com.example.stackapp.pages;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static java.util.Objects.requireNonNull;

public class Login extends Application {

    private static final String PAGE = "log-in.fxml";
    private static Stage window;

    @Override
    public void start(Stage stage) throws Exception {
        //Set primary window;
        window = stage;
        Parent root = FXMLLoader.load(requireNonNull(getClass().getResource(PAGE)));
        window.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}