package connect.net.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Connect {
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
    public void InsertUser(String firstname, String lastname, String username,String password) {
        String query = "INSERT INTO users (ID, first_name, last_name, password, startat, endat, username) values (?, ?, ?, ?, ?, ?, ?)";
        // create the mysql insert preparedstatement
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, 2);
            pstmt.setString(2, firstname);
            pstmt.setString(3, lastname);
            pstmt.setString(4, password);
            pstmt.setTimestamp(5,null);
            pstmt.setTimestamp(6,null);
            pstmt.setString(7, username);
            pstmt.executeUpdate();
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