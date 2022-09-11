package connect.net.sqlite;

import com.example.stackapp.model.BoxDTO;
import com.example.stackapp.model.BoxData;
import com.example.stackapp.model.UserData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static connect.net.sqlite.Sql.*;
import static java.lang.String.valueOf;

public class Connect {

    private static final String URL = "jdbc:sqlite:stackAppdbv1.db";

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public List<BoxDTO> showBox(String shelfId) {
        List<BoxDTO> boxes = new ArrayList<>();
        try {
            connectAndSetStatementFor(SHOW_BOX);
            ps.setString(1, shelfId);
            rs = ps.executeQuery();
            while(rs.next()) {
                boxes.add(new BoxDTO(
                        rs.getInt("b_id"),
                        rs.getInt("client_id")));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            close();
        }
        return boxes;
    }

    public void insertBox(BoxData box) {
        try {
            connectAndSetStatementFor(INSERT_BOX);
            ps.setString(1, valueOf(box.getId()));
            ps.setString(2, valueOf(box.getClientId()));
            ps.setString(3, box.getDateFrom());
            ps.setString(4, box.getDateEnd());
            ps.setString(5, box.getFulfillment());
            ps.setString(6, box.getInfoNote());
            ps.setString(7, box.getWeight());
            ps.setString(8, box.getShelfId());
            ps.setString(9, box.getStatus());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            close();
        }
    }

    public void updateShelfId(String shelfId, long boxId) {
        try {
            connectAndSetStatementFor(UPDATE_SHELF);
            ps.setString(1, shelfId);
            ps.setLong(2, boxId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            close();
        }
    }

    public BoxData searchForBox(long boxId) {
        try {
            connectAndSetStatementFor(SELECT_BOX);
            ps.setLong(1, boxId);
            rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) {
                return null;
            }

            return new BoxData(
                    boxId,
                    rs.getLong("client_id"),
                    rs.getString("date_from"),
                    rs.getString("date_end"),
                    rs.getString("fulfillment"),
                    rs.getString("status"),
                    rs.getString("info_note"),
                    rs.getString("weight"),
                    rs.getString("shelf_id")
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            close();
        }
    }

    //trying to give an objective view how much space are taken
    public int capacityOf(String shelf) {
        try {
            connectAndSetStatementFor(SELECT_COUNT + shelf + "%" + "'");
            rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) {
                return 0;
            }

            return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        } finally {
            close();
        }
    }

    public void deleteUser(int id) {
        try {
            connectAndSetStatementFor(DELETE_USER);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            close();
        }
    }

    public void updateUserTable(UserData user) {
        try {
            connectAndSetStatementFor(UPDATE_USER);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getUserName());
            ps.setString(5, valueOf(user.getId()));
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            close();
        }
    }

    public String checkLogin(String username) {
        try {
            connectAndSetStatementFor(LOGIN_CHECK);
            ps.setString(1, username);
            rs = ps.executeQuery();
            return rs.getString("password");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            close();
        }
        return null;
    }

    public void insertUser(UserData user) {
        try {
            connectAndSetStatementFor(INSERT_USER);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getUserName());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            close();
        }
    }

    public List<UserData> showAllUsers() {
        List<UserData> users = new ArrayList<>();
        try {
            connectAndSetStatementFor(SELECT_USER);
            rs = ps.executeQuery();
            while (rs.next()) {
                users.add(new UserData(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("password"),
                        rs.getString("username")));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            close();
        }
        return users;
    }

    private void connectAndSetStatementFor(String SQL) throws SQLException {
        conn = DriverManager.getConnection(URL);
        ps = conn.prepareStatement(SQL);
    }

    private void close() {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}