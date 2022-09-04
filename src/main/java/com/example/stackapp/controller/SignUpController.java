package com.example.stackapp.controller;

import com.example.stackapp.Main;
import com.example.stackapp.model.UserData;
import connect.net.sqlite.Connect;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SignUpController {

    private static final String LOGIN_PAGE = "pages/log-in.fxml";
    private static final String MAIN_PAGE = "main.fxml";
    private static final Main window = new Main();

    Connect conn;
    UserData user;
    String username, password, firstname, lastname;

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
    public void CreateUser() {

        username = tf_username.getText();
        password = tf_password.getText();
        firstname = tf_first_name.getText();
        lastname = tf_last_name.getText();


        if (!username.isEmpty() && !password.isEmpty() && !firstname.isEmpty() && !lastname.isEmpty()) {
            conn = new Connect();

            user = new UserData(firstname, lastname, username, password);
            conn.insertUser(user);
            System.out.println("SUCCESSFULLY");
            try {
                window.changePage(MAIN_PAGE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("FAILED");
            try {
                window.changePage(LOGIN_PAGE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
