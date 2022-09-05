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
        return !username.trim().isEmpty() && !password.trim().isEmpty();
    }

    public void login() {
        String username = tf_username.getText();
        String password = pf_password.getText();

        System.out.println("USER ENTERED VARIABLED " + username + " " + password);
        /// CONNECT TO DABASE, RECEIVE INFO
        if (!isValidCredentials(username,password)) {
            System.out.println("Username or password is missing");
            l_errorText.setVisible(true);
            return;
        }
        l_errorText.setVisible(false);
        // connect to DB
        Connection conn;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
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
                        window.changePage(SAMPLE_PAGE);
                    } else {
                        System.out.println("Passwords did not match!");
                        l_errorText.setText("Passwords did not match!");
                        l_errorText.setVisible(true);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Exception!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}