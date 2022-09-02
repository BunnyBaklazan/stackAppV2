package com.example.stackapp.pages;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

public class App extends Application {

    private static final String PAGE = "columns_page.fxml";
    private static Stage window;
    @Override
    public void start(Stage stage) throws IOException {
        window= stage;
        Parent root = FXMLLoader.load(requireNonNull(getClass().getResource(PAGE)));
        window.setScene(new Scene(root));

        stage.setTitle("Hello StackAPP");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}