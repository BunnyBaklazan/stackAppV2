package com.example.stackapp.controller;

import com.example.stackapp.Main;
import connect.net.sqlite.Connect;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.*;
import java.util.prefs.Preferences;

public class LoginController {
    private static final String MAIN_PAGE = "main.fxml";
    private static final String SAMPLE_PAGE = "pages/sample.fxml";
    private static final Preferences userPreferences = Preferences.userRoot();
    private static final Main window = new Main();

    @FXML
    private TextField tf_username;

    @FXML
    private PasswordField pf_password;

    @FXML
    private Label l_errorText;

    public void backToMainPage() throws IOException {
        window.changePage(MAIN_PAGE);
    }

    public static boolean isValidCredentials(String username, String password) {
        return !username.trim().isEmpty() && !password.trim().isEmpty();
    }

    public void login() {
        String username = tf_username.getText();
        String password = pf_password.getText();
        ResultSet result = null;

        System.out.println("USER ENTERED VARIABLED " + username + " " + password);

        if (!isValidCredentials(username, password)) {
            System.out.println("Username or password is missing");
            l_errorText.setVisible(true);
            return;
        }
        l_errorText.setVisible(false);

        try {
            result = Connect.checkLogin(username);

            if (!result.isBeforeFirst()) {
                System.out.println("User is not found!");
                l_errorText.setText("User is not found!");
                l_errorText.setVisible(true);

            } else {
                while (result.next()) {
                    String retrievedPassword = result.getString("password");
                    //throw new RuntimeException(e);

                    // database update is needed otherwise it won't let you in :(
                    if (BCrypt.checkpw(password, retrievedPassword)) {
                        //if (retrievedPassword.equals(password)) {
                        System.out.println("User found and password is correct.");
                        userPreferences.put("username", username);
                        if (username.equals("asd")) { //need to change to "admin"
                            userPreferences.put("role", "admin");
                            window.changePage(SAMPLE_PAGE);
                        } else {
                            userPreferences.put("role", "worker");
                            window.changePage(SAMPLE_PAGE);
                        }

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