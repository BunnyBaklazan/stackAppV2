package connect.net.sqlite;

import com.example.stackapp.model.BoxData;
import com.example.stackapp.model.UserData;

import java.sql.*;

import static java.lang.String.valueOf;

public class Connect {
    private static final String SELECT_BOX
            = "SELECT * FROM box WHERE b_id = ?";

    private static final String INSERT_BOX
            ="INSERT INTO box (b_id, client_id, date_from, date_end," +
            " fulfillment, info_note, weight, shelf_id, status) " +
            "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_COUNT
            = "SELECT COUNT(*) FROM box WHERE shelf_id LIKE '";


    private static final String INSERT_USER
            = "INSERT INTO users (first_name, last_name, password, username) values (?, ?, ?, ?)";

    private static final String SELECT_USER
            = "SELECT * FROM users";

    public static final String DELETE_USER
            = "DELETE FROM users WHERE id = ?";

    public static final String UPDATE_USER
            = "UPDATE users SET first_name = ? , "
            + "last_name = ? , "
            + "password = ? "
            + "WHERE id = ?";

    public static  final String LOGIN_CHECK
            ="SELECT password FROM users WHERE USERNAME = ?";


    private static Connection connect() {
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

    public static void insertUser(UserData user) {

        try {
            Connection conn = connect();
            PreparedStatement statement = conn.prepareStatement(INSERT_USER);

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getUserName());
            statement.executeUpdate();

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

    }
    public static ResultSet showAllUsers() {
        try{
            Connection conn = connect();
            PreparedStatement statement = conn.prepareStatement(SELECT_USER);
            return statement.executeQuery();

        } catch (SQLException e){
            System.out.println(e.getMessage());
            return null;

        }

    }

    public static String giveUsername(){
        try{
            Connection conn = connect();
            PreparedStatement statement = conn.prepareStatement(SELECT_USER);
            ResultSet result = statement.executeQuery();
            return result.getString("username");

        } catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void updateBox(BoxData box){
        //insert

    }

    //create new box in the db
    public static void insertBox(BoxData box) {
        try{
            Connection conn = connect();
            PreparedStatement statement = conn.prepareStatement(INSERT_BOX);

            statement.setString(1, valueOf(box.getId()));
            statement.setString(2, valueOf(box.getClientId()));
            statement.setString(3, box.getDateFrom());
            statement.setString(4, box.getDateEnd());
            statement.setString(5, box.getFulfillment());
            statement.setString(6, box.getInfoNote());
            statement.setString(7, box.getWeight());
            statement.setString(8, box.getShelfId());
            statement.setString(9, box.getStatus());
            statement.executeUpdate();

        }catch (SQLException e){
            System.out.println(e);
        }
    }

    public static BoxData searchForBox(long boxId) {

        try{
            ResultSet result;
            Connection conn = connect();
            PreparedStatement statement = conn.prepareStatement(SELECT_BOX);
            statement.setLong(1, boxId);
            result = statement.executeQuery();

            if(!result.isBeforeFirst()){
                System.out.println("Answer from connect! Bro we don't have it");
                return null;
            }

            return new BoxData(
                    boxId,
                    result.getLong("client_id"),
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
    public int capacityOf(String shelf) {
        try{
            ResultSet result = null;
            Connection conn = connect();
            PreparedStatement statement = conn.prepareStatement(SELECT_COUNT+shelf+"%"+"'");
            result = statement.executeQuery();

            if(!result.isBeforeFirst()){
                return 0;
            }

            return result.getInt(1);

        } catch(SQLException e){
            System.out.println(e);
            return 0;
        }
    }

    public static void deleteUser(int id) {
        try {
            Connection conn = connect();
            PreparedStatement statement = conn.prepareStatement(DELETE_USER);

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void updateUserTable(UserData user){
        try {
            Connection conn = connect();
            PreparedStatement statement = conn.prepareStatement(UPDATE_USER);

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPassword());
            statement.setString(4, valueOf(user.getId()));
            statement.executeUpdate();

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static ResultSet checkLogin(String username){
        try {
            Connection conn = connect();
            PreparedStatement statement = conn.prepareStatement(LOGIN_CHECK);
            statement.setString(1, username);

            return statement.executeQuery();

        } catch(SQLException e){
            System.out.println(e.getMessage());
            return null;

        }

    }



    public static void main(String[] args) {
        //connect();
        //blackpink in the area
    }
}