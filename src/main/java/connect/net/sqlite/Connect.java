package connect.net.sqlite;

import com.example.stackapp.model.UserData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Connect {

    private static final String INSERT_USER
            = "INSERT INTO users (ID, first_name, last_name, password, startat, endat, username) values (?, ?, ?, ?, ?, ?, ?)";

    /**
     * Connect to a sample database
     */
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

            statement.setInt(1, 2);
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getPassword());
            statement.setTimestamp(5,null);
            statement.setTimestamp(6,null);
            statement.setString(7, user.getUserName());
            statement.executeUpdate();

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //connect();
    }
}