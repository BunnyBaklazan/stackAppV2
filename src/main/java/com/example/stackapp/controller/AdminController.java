package com.example.stackapp.controller;

import com.example.stackapp.Main;
import com.example.stackapp.model.UserData;
import connect.net.sqlite.Connect;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    private void delete() {
        UserData user = table_users.getSelectionModel().getSelectedItem();

        Connect conn = new Connect();
        conn.deleteUser(user.getId());

        tf_first_name.setText("");
        tf_last_name.setText("");
        tf_password.setText("");
        tf_username.setText("");

        //put execution of query here
        showUsersTable();
    }

    @FXML
    private void update() {

        Connect.updateUserTable(
               new UserData(
                   tf_first_name.getText(),
                   tf_last_name.getText(),
                   tf_password.getText(),
                   tf_username.getText()
               )
        );

        showUsersTable();
    }

    private String encryptPass(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @FXML
    private void insert()  {
        Connect conn = new Connect();

        UserData user = new UserData(
                tf_first_name.getText(),
                tf_last_name.getText(),
                encryptPass(tf_password.getText()),
                tf_username.getText());

        conn.insertUser(user);
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

        Connect conn = new Connect();
        ResultSet result = conn.showAllUsers();

        try{
            while(result.next()) {
                UserData user = new UserData(result.getInt("id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("password"),
                        result.getString("username")
                );

                usersList.add(user);
            }

        }  catch (Exception e) {
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
        String name= tf_first_name.getText().toLowerCase();
        String surname= tf_last_name.getText().toLowerCase();
        tf_username.setText(name.toLowerCase()+"."+surname.toLowerCase());
    }
}
