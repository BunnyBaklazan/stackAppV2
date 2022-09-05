package com.example.stackapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

public class Main extends Application {

    private static final String MAIN_PAGE = "main.fxml";
    private static Stage window;


    @Override
    public void start(Stage stage) throws Exception {
        window = stage;

        Parent root = FXMLLoader.load(requireNonNull(getClass().getResource(MAIN_PAGE)));
        window.setScene(new Scene(root));

        String css = this.getClass().getResource("colors.css").toExternalForm();
        window.getScene().getStylesheets().add(css);

        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void changePage(String pageName) throws IOException {
        Parent parent = FXMLLoader.load(requireNonNull(getClass().getResource(pageName)));
        window.getScene().setRoot(parent);

        String css= this.getClass().getResource("colors.css").toExternalForm();
        window.getScene().getStylesheets().add(css);
    }

}
 /// JAVA