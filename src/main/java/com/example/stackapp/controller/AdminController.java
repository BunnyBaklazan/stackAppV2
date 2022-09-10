package com.example.stackapp.controller;

import com.example.stackapp.Main;
import com.example.stackapp.model.UserData;
import connect.net.sqlite.Connect;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.ResultSet;
import java.util.prefs.Preferences;

public class AdminController {
    private static final Main window = new Main();
    protected final static Preferences userPreferences = Preferences.userRoot();

    @FXML
    private Button b1, b2, d1, d2, d3, d4, d5, d6, d7, d8, showSearchBox_Btn, addWorkerBtn = new Button();

    @FXML
    private TextField tf_first_name;

    @FXML
    private TextField tf_last_name;

    @FXML
    private PasswordField pass_field;

    @FXML
    private TextField tf_username;

    @FXML
    private Button btn_insert;

    @FXML
    private Button btn_update;

    @FXML
    private Button btn_delete;

    @FXML
    private TableView<UserData> table_users;

    @FXML
    private TableColumn<UserData, Integer> tc_id;

    @FXML
    private TableColumn<UserData, String> tc_first_name;

    @FXML
    private TableColumn<UserData, String> tc_last_name;

    @FXML
    private TableColumn<UserData, String> tc_password;

    @FXML
    private TableColumn<UserData, String> tc_username;

    @FXML
    private Label label_error;

    @FXML
    private void handleMouseAction() {
        UserData user = table_users.getSelectionModel().getSelectedItem();
        if (user != null) {
            tf_first_name.setText(user.getFirstName());
            tf_last_name.setText(user.getLastName());
            pass_field.setText(user.getPassword());
            tf_username.setText(user.getUserName());
        }
    }

    private boolean isValidUserData() {
        String firstName = tf_first_name.getText().trim();
        String lastName = tf_last_name.getText().trim();
        String password = pass_field.getText().trim();
        String username = tf_username.getText().trim();
        if (firstName.equals("") || lastName.equals("") || password.equals("") || username.equals("")) {
            return  false;
        }
        return true;
    }

    private void setEmptyStrings() {
        tf_first_name.setText("");
        tf_last_name.setText("");
        pass_field.setText("");
        tf_username.setText("");
    }

    @FXML
    private void delete() {
        UserData user = table_users.getSelectionModel().getSelectedItem();
        Connect.deleteUser(user.getId());
        setEmptyStrings();
        showUsersTable();
    }

    @FXML
    private void update() {
        Connect.updateUserTable(
                new UserData(
                        tf_first_name.getText(),
                        tf_last_name.getText(),
                        pass_field.getText(),
                        tc_id.getText()
                )
        );

        setEmptyStrings();
        showUsersTable();
    }

    private String encryptPass(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @FXML
    private void insert() {
        UserData user = new UserData(
                tf_first_name.getText(),
                tf_last_name.getText(),
                encryptPass(pass_field.getText()),
                tf_username.getText());

        if(!isValidUserData()) {
            System.out.println("Invalid Credentials");
            label_error.setText("Error! Enter all text fields!");
        } else {
            System.out.println("Inserting user");
            label_error.setText("");
            Connect.insertUser(user);
            setEmptyStrings();
            showUsersTable(); // show table after insertion
        }
    }

    @FXML
    public void initialize() {
        String role = userPreferences.get("role", null);
        if (role.equals("admin")) {
            System.out.println("You are in the admin dashboard");
            addWorkerBtn.setVisible(true);
            showUsersTable();
        } else {
            // show content from sample controller
        }
    }

    public ObservableList<UserData> getUsersData() {
        ObservableList<UserData> usersList = FXCollections.observableArrayList();

        //Connect conn = new Connect();
        ResultSet result = Connect.showAllUsers();

        try {
            while (result.next()) {
                UserData user = new UserData(
                        result.getInt("id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("password"),
                        result.getString("username")
                );

                usersList.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usersList;
    }

    public void showUsersTable() {
        ObservableList<UserData> list = getUsersData();

        tc_id.setCellValueFactory(new PropertyValueFactory<UserData, Integer>("id"));
        tc_first_name.setCellValueFactory(new PropertyValueFactory<UserData, String>("firstName"));
        tc_last_name.setCellValueFactory(new PropertyValueFactory<UserData, String>("lastName"));
        tc_password.setCellValueFactory(new PropertyValueFactory<UserData, String>("password"));
        tc_username.setCellValueFactory(new PropertyValueFactory<UserData, String>("userName"));

        table_users.setItems(list);
    }

    @FXML
    void setUsername() {
        String name = tf_first_name.getText().toLowerCase();
        String surname = tf_last_name.getText().toLowerCase();
        tf_username.setText(name.toLowerCase() + "." + surname.toLowerCase());
    }
}
