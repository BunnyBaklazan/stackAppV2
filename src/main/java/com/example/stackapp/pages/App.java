package com.example.stackapp.pages;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader= new FXMLLoader(App.class.getResource("view.fxml"));
        Scene scene= new Scene(fxmlLoader.load(), 900, 700);

        stage.setTitle("Hello StackAPP");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}