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
        window.backToLoginPage(MAIN_PAGE);
    }

    public void openSignUpPage() throws IOException {
        window.openSignUpPage(SIGNUP_PAGE);
    }

}