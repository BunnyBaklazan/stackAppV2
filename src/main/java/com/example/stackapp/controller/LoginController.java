package com.example.stackapp.controller;

import com.example.stackapp.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;

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
        window.changePage(MAIN_PAGE);
    }

    public void refactorBACK() throws IOException {
        window.changePage(SIGNUP_PAGE);
    }

    public void login() {
        String username = tf_username.getText();
        String password = pf_password.getText();

        System.out.println("USER ENTERED VARIABLED " + username + " " + password);
        /// CONNECT TO DABASE, RECEIVE INFO
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            System.out.println("Username or password is missing");
            l_errorText.setVisible(true);
            return;
        }
        l_errorText.setVisible(false);
        // connect to DB
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:stackAppdbv1.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            preparedStatement = conn.prepareStatement("SELECT password FROM users WHERE USERNAME = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User is not found!");
                l_errorText.setText("User is not found!");
                l_errorText.setVisible(true);
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    if (retrievedPassword.equals(password)) {
                        System.out.println("User found and password is correct.");
                        //window.changePage(SIGNUP_PAGE);
                    } else {
                        System.out.println("Passwords did not match!");
                        l_errorText.setText("Passwords did not match!");
                        l_errorText.setVisible(true);
                        //window.changePage(MAIN_PAGE);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Exception!");
        }
    }
}