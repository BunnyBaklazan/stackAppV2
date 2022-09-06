package com.example.stackapp.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.prefs.Preferences;
import com.example.stackapp.Main;
import com.example.stackapp.model.User;
import com.example.stackapp.model.UserData;
import connect.net.sqlite.Connect;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.Arrays;

public class AdminController {
    private static final Main window = new Main();
    protected final static Preferences userPreferences = Preferences.userRoot();
    @FXML
    private Button b1, b2, d1, d2, d3, d4, d5, d6, d7, d8, showSearchBox_Btn, addWorkerBtn = new Button();

    @FXML
    private TextField tf_id;
    @FXML
    private TextField tf_first_name;
    @FXML
    private TextField tf_last_name;
    @FXML
    private TextField tf_password;
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
        Connection conn = Connect.connect();
        String query = "SELECT * FROM users";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = conn.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            UserData user;
            while(resultSet.next()) {
                user = new UserData(resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("password"),
                        resultSet.getString("username"));
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

    public AdminController() {

    }
}
