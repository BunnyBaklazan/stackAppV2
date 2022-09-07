package com.example.stackapp.controller;

import com.example.stackapp.Main;
import connect.net.sqlite.Connect;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    private static final String MAIN_PAGE = "main.fxml";
    private static final String SAMPLE_PAGE = "pages/sample.fxml";
    private static final Main window = new Main();
    @FXML
    private Button button_login;

    @FXML
    private TextField tf_username;

    @FXML
    private PasswordField pf_password;

    @FXML
    private Label l_errorText;

    public void backToMainPage() throws IOException {
        window.changePage(MAIN_PAGE);
    }

    public void refactorBACK() throws IOException {
        window.changePage(SAMPLE_PAGE);
    }

    public static boolean isValidCredentials(String username, String password) {
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    public void login() {

        Connect conn = new Connect();
        String username = tf_username.getText();
        String password = pf_password.getText();
        ResultSet resultSet = null;

        resultSet = conn.searchForUser(username, password);

        System.out.println("USER ENTERED VARIABLES " + username + " " + password);

        // Check if username and password are written
        if (!isValidCredentials(username, password)) {
            System.out.println("Username or password is missing");
            l_errorText.setVisible(true);
            return;
        }
        l_errorText.setVisible(false);

        try {
            //some magic it works only like that, and not otherwise
            if(resultSet.getString("password") == null ||
                    resultSet.getString("username") == null) {
                System.out.println("Something is not right!");
                l_errorText.setText("Check password or username");
                l_errorText.setVisible(true);

            } else if(resultSet.getString("password").equals(password) &&
                    resultSet.getString("username").equals(username)) {
                System.out.println("Everything is fine");
                window.changePage(SAMPLE_PAGE);

            }

        } catch (SQLException | IOException e) {
            System.out.println("Exception!");
        }
    }
}