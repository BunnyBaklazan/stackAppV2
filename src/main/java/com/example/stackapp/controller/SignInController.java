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
    private final Connect connect = new Connect();

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
    public void login() throws IOException {
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

        String pass = connect.checkLogin(username);
        if (pass != null && BCrypt.checkpw(password, pass)) {
            System.out.println("User found and password is correct.");
            userPreferences.put("username", username);

            if (username.equals("admin")) { //need to change to "admin"
                userPreferences.put("role", "admin");
            } else {
                userPreferences.put("role", "worker");
            }
            window.changePage(SAMPLE_PAGE);
        } else {
            errorTxtField.setText("Username or password incorrect!");
            errorTxtField.setVisible(true);
        }
    }
}