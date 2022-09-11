package com.example.stackapp.controller;

import com.example.stackapp.Main;
import connect.net.sqlite.Connect;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.*;
import java.util.prefs.Preferences;

public class SignInController {
    private static final String SAMPLE_PAGE = "pages/sample.fxml";
    private static final Preferences userPreferences = Preferences.userRoot();
    private static final Main window = new Main();

    @FXML
    private TextField tf_username;

    @FXML
    private PasswordField pf_password;

    @FXML
    private TextField errorTxtField;

    public static boolean isValidCredentials(String username, String password) {
        return !username.trim().isEmpty() && !password.trim().isEmpty();
    }

    @FXML
    public void login() {
        String username = tf_username.getText();
        String password = pf_password.getText();
        ResultSet result = null;

        if (!isValidCredentials(username, password)) {
            System.out.println("Username or password is missing");
            errorTxtField.setVisible(true);
            errorTxtField.setText("Username or password is missing!");
            return;
        }
        errorTxtField.setVisible(false);

        try {
            result = Connect.checkLogin(username);

            if (!result.isBeforeFirst()) {
                errorTxtField.setText("User is not found!");
                errorTxtField.setVisible(true);

            } else {
                while (result.next()) {
                    String retrievedPassword = result.getString("password");
                    if (BCrypt.checkpw(password, retrievedPassword)) {
                        System.out.println("User found and password is correct.");
                        userPreferences.put("username", username);

                        if (username.equals("admin")) {
                            userPreferences.put("role", "admin");
                        } else {
                            userPreferences.put("role", "worker");
                        }
                        window.changePage(SAMPLE_PAGE);
                    } else {
                        errorTxtField.setText("Passwords did not match!");
                        errorTxtField.setVisible(true);
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