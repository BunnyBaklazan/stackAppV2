package com.example.stackapp.controller;

import com.example.stackapp.Main;
import connect.net.sqlite.Connect;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpController {

    private static final String LOGIN_PAGE = "pages/log-in.fxml";
    private static final String MAIN_PAGE = "main.fxml";
    private static final Main window = new Main();

    @FXML
    private Button button_signup;

    @FXML
    private Button button_log_in;

    @FXML
    private TextField tf_username;

    @FXML
    private TextField tf_password;

    @FXML
    private TextField tf_first_name;

    @FXML
    private TextField tf_last_name;

    @FXML
    private Label l_errorText;

    public void backToLoginPage() throws IOException {
        window.changePage(LOGIN_PAGE);
    }
    public void CreateUser() throws IOException {

        String username = tf_username.getText();
        String password = tf_password.getText();
        String firstname = tf_first_name.getText();
        String lastname = tf_last_name.getText();

        System.out.printf("USER ENTERED VARIABLES: %s %s %s %s \n", username, password, firstname, lastname);

        if (!username.isEmpty() && !password.isEmpty() && !firstname.isEmpty() && !lastname.isEmpty()) {
            Connect conn = new Connect();
            conn.InsertUser(firstname,lastname, username, password);
            System.out.println("SUCCESSFULLY");
            //window.changePage(MAIN_PAGE);
        } else {
            System.out.println("Lohs");
            window.changePage(LOGIN_PAGE);

        }
    }
}
