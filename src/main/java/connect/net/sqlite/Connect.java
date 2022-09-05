package connect.net.sqlite;

import com.example.stackapp.model.UserData;

import java.sql.*;

public class Connect {

    private static final String INSERT_USER
            = "INSERT INTO users (first_name, last_name, password, username) values (?, ?, ?, ?)";

    private static final String SELECT_USER
            = "SELECT username, password FROM users WHERE username = ? AND password = ?";
    private Connection connect() {
        String url = "jdbc:sqlite:stackAppdbv1.db";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insertUser(UserData user) {
        try {
            Connection conn = connect();
            PreparedStatement statement = conn.prepareStatement(INSERT_USER);

            //statement.setInt(1, 4);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getUserName());
            statement.executeUpdate();

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

    }
    public ResultSet searchForUser(String userName, String password){
        ResultSet result = null;
        try{
            Connection conn = connect();
            PreparedStatement statement = conn.prepareStatement(SELECT_USER);
            statement.setString(1, userName);
            statement.setString(2, password);
            return result = statement.executeQuery();

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    public void searchForBox(){

    }
    public static void main(String[] args) {
        //connect();
    }
}