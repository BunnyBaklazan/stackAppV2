package com.example.stackapp.controller;

import com.example.stackapp.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    private static final String MAIN_PAGE = "main.fxml";
    private static final String SIGNUP_PAGE = "pages/sign-up.fxml";
    private static final Main window = new Main();

    private static final String TEST_USERNAME = "oleg";
    private static final String TEST_PASSWORD = "123";

    @FXML
    private Button button_login;

    @FXML
    private Button button_sign_up;

    @FXML
    private TextField tf_username;

    @FXML
    private PasswordField pf_password;

    @FXML
    private Label l_errorText;

    public void backToLoginPage() throws IOException {
        window.changePage(MAIN_PAGE);
    }

    public void refactorBACK() throws IOException {
        window.changePage(SIGNUP_PAGE);
    }

    public void getMeCredentials() throws IOException {
        String username = tf_username.getText();
        String password = pf_password.getText();

        System.out.println("USER ENTERED VARIABLED " + username + " " + password);
        /// CONNECT TO DABASE, RECEIVE INFO

        if (username.equals(TEST_USERNAME) && password.equals(TEST_PASSWORD)) {
            System.out.println("SUCCESFULL");
            window.changePage(SIGNUP_PAGE);
        } else {
            System.out.println("BAD CREDENTIALS");
            window.changePage(MAIN_PAGE);
        }

    }

}