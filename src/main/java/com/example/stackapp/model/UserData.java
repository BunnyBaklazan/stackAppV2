package com.example.stackapp.model;

public class UserData {

    private int id;
    private String firstName;
    private String lastName;
    private String password;
    private String userName;


    public UserData(String firstName, String lastName, String password, String userName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userName = userName;
    }

    public UserData(int id,  String firstName, String lastName, String password, String userName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
