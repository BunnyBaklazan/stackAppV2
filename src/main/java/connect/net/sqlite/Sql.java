package connect.net.sqlite;

public class Sql {

    public static final String SELECT_BOX
            = "SELECT * FROM box WHERE b_id = ?";

    public static final String INSERT_BOX
            = "INSERT or REPLACE INTO box (b_id, client_id, date_from, date_end," +
            " fulfillment, info_note, weight, shelf_id, status) " +
            "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String SELECT_COUNT
            = "SELECT COUNT() FROM box WHERE shelf_id LIKE '";

    public static final String UPDATE_SHELF
            = "UPDATE box SET shelf_id = ? WHERE b_id = ?";

    public static final String SHOW_BOX
            = "SELECT client_id, b_id FROM box WHERE shelf_id = ?";

    public static final String INSERT_USER
            = "INSERT INTO users (first_name, last_name, password, username) values (?, ?, ?, ?)";

    public static final String SELECT_USER
            = "SELECT * FROM users";

    public static final String DELETE_USER
            = "DELETE FROM users WHERE id = ?";

    public static final String UPDATE_USER
            = "UPDATE users SET first_name = ? , "
            + "last_name = ? , "
            + "password = ? ,"
            + " username = ? "
            + "WHERE id = ?";

    public static final String LOGIN_CHECK
            = "SELECT password FROM users WHERE USERNAME = ?";
}
