package com.example.stackapp.controller;

import java.sql.*;
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
import org.w3c.dom.events.MouseEvent;

import java.util.Arrays;

import static connect.net.sqlite.Connect.connect;

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
    private void handleMouseAction() {
        UserData user = table_users.getSelectionModel().getSelectedItem();
        tf_first_name.setText(user.getFirstName());
        tf_last_name.setText(user.getLastName());
        tf_password.setText(user.getPassword());
        tf_username.setText(user.getUserName());
    }

    //private void executeQuery(String query) { // for refactoring
    //    Connection conn = getConnection();
    //    PreparedStatement ps;
    //    try {
    //        ps = conn.prepareStatement(query);
    //        ps.executeUpdate();
    //    } catch(Exception e) {
    //        e.printStackTrace();
    //    }
    //     }

    @FXML
    private void delete() throws SQLException {
        UserData user = table_users.getSelectionModel().getSelectedItem();
        String query = "DELETE FROM users WHERE id = ?";
        Connection conn = connect();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();

            tf_first_name.setText("");
            tf_last_name.setText("");
            tf_password.setText("");
            tf_username.setText("");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //put execution of query here
        showUsersTable();
    }

    @FXML
    private void update() {
        Connection conn = connect();
        String query = "UPDATE users SET "
                + " first_name ='" + tf_first_name.getText() + "' "
                + ", last_name ='"+ tf_last_name.getText() + "' "
                + ", password ='" + tf_password.getText()+"' "
                + " WHERE username='" + tf_username.getText()+"' ";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = conn.prepareStatement(query);
           // preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        showUsersTable();
    }

    @FXML
    private void insert() throws SQLException {
        UserData user = table_users.getSelectionModel().getSelectedItem();
        try{
        Connection conn = connect();
        String query = "INSERT INTO users (first_name, last_name, password, username) values (?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(query);

        statement.setString(1, tf_first_name.getText());
        statement.setString(2, tf_last_name.getText());
        statement.setString(3, tf_password.getText());
        statement.setString(4, tf_first_name.getText() + tf_last_name.getText());
        statement.executeUpdate();

    } catch(SQLException e){
        System.out.println(e.getMessage());
    }
        showUsersTable(); // show table after insertion
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
        Connection conn = connect();
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
}
