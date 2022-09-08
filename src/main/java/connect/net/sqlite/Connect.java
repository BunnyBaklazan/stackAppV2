package connect.net.sqlite;

import com.example.stackapp.model.BoxData;
import com.example.stackapp.model.UserData;

import java.sql.*;

public class Connect {
    private static final String INSERT_USER
            = "INSERT INTO users (first_name, last_name, password, username) values (?, ?, ?, ?)";

    private static final String SELECT_USER
            = "SELECT username, password FROM users WHERE username = ? AND password = ?";

    private static final String SELECT_BOX
            = "SELECT * FROM box WHERE b_id = ?";

    private static final String INSERT_BOX
            ="";

    private static final String SELECT_COUNT
            ="SELECT COUNT(*) FROM box WHERE shelf_id LIKE ?%";


    public static Connection connect() {
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
        try{
            Connection conn = connect();
            PreparedStatement statement = conn.prepareStatement(SELECT_USER);
            statement.setString(1, userName);
            statement.setString(2, password);
            return statement.executeQuery();

        } catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }

    }

    //create new box in the db
    public void insertBox(BoxData box){
        try{
            Connection conn = connect();
            PreparedStatement statement = conn.prepareStatement(INSERT_BOX);
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    public BoxData searchForBox(long boxId){

        try{
            ResultSet result;
            Connection conn = connect();
            //Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            PreparedStatement statement = conn.prepareStatement(SELECT_BOX);
            System.out.println("Blackpink");
            statement.setLong(1, boxId);
            result = statement.executeQuery();

            System.out.println(result.getRow());
            if(result.getRow() == 0){
                System.out.println("Bro we don't have it");
                return null;
            }

            return new BoxData(
                    boxId,
                    result.getLong("client_id"),
                    //some troubles with date
                    result.getString("date_from"),
                    result.getString("date_end"),
                    result.getString("fulfillment"),
                    result.getString("status"),
                    result.getString("info_note"),
                    result.getString("weight"),
                    result.getString("shelf_id")
            );

        } catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    //trying to give an objective view how much space are taken
    public ResultSet stack_capacity(String stack){
        try{
           // ResultSet result = null;
            Connection conn = connect();
            PreparedStatement statement = conn.prepareStatement(SELECT_COUNT);
            statement.setString(1, stack);
            return statement.executeQuery();

        } catch(SQLException e){
            System.out.println(e);
        }

        return null;
    }

    //max capacity 9 like shelf is 
    public ResultSet shelf_capacity(String shelf){
        try{
            // ResultSet result = null;
             Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SELECT_COUNT);
             statement.setString(1, shelf);
             return statement.executeQuery();
 
         } catch(SQLException e){
             System.out.println(e);
         }

        return null;
    }


    public static void main(String[] args) {
        //connect();
    }
}