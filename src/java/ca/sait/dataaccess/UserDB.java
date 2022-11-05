package ca.sait.dataaccess;

import ca.sait.models.Role;
import ca.sait.models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rehan
 */
public class UserDB {

    public List<User> getAll() throws Exception {

        List<User> userArray = new ArrayList<>();

        ConnectionPool cp = ConnectionPool.getInstance();
        Connection conn = cp.getConnection();

        PreparedStatement ps = null;

        ResultSet rs = null;

        String sql = "SELECT * FROM userdb_user INNER JOIN userdb_role ON userdb_role.role_id = userdb_user.role;";

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String email = rs.getString(1);
                boolean active = rs.getBoolean(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String password = rs.getString(5);
                int roleID = rs.getInt(6);
                String roleName = rs.getString(7);

                Role role = new Role(roleID, roleName);

                User user = new User(email, active, firstName, lastName, password, role);

                userArray.add(user);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(conn);
        }

        return userArray;
    }

    public User get(String email) throws Exception {

        User user = null;

        ConnectionPool cp = ConnectionPool.getInstance();
        Connection conn = cp.getConnection();

        PreparedStatement ps = null;

        ResultSet rs = null;

        String sql = "SELECT * FROM userdb INNER JOIN userdb_role ON userdb_role.role_id = user_db.role WHERE email = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);

            rs = ps.executeQuery();

            if (rs.next()) {
                boolean active = rs.getBoolean(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String password = rs.getString(5);
                int roleID = rs.getInt(6);
                String roleName = rs.getString(7);

                Role role = new Role(roleID, roleName);

                user = new User(email, active, firstName, lastName, password, role);

            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(conn);
        }

        return user;
    }

    public boolean insert(User user) throws Exception {

        ConnectionPool cp = ConnectionPool.getInstance();
        Connection conn = cp.getConnection();

        PreparedStatement ps = null;

        String sql = "INSERT INTO userdb_user (`email`, `first_name`, `last_name`, `password`, `role`) VALUES (?, ?, ?, ?, ?);";

        boolean inserted = false;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getRole().getRoleID());

            if (ps.executeUpdate() == 0) {
                inserted = true;
            } else {
                inserted = false;
            }

        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(conn);
        }

        return inserted;
    }

    public boolean update(User user) throws Exception {

        ConnectionPool cp = ConnectionPool.getInstance();
        Connection conn = cp.getConnection();

        PreparedStatement ps = null;

        String sql = "UPDATE userdb_user SET `first_name` = ? `last_name` = ? `password` = ? `role` = ? WHERE `email` = ?;";

        boolean updated = false;

        try {
            ps = conn.prepareStatement(sql);

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getRole().getRoleID());
            ps.setString(5, user.getEmail());

            if (ps.executeUpdate() == 0) {
                updated = true;
            } else {
                updated = false;
            }

        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(conn);
        }

        return updated;
    }

    public boolean delete(User user) throws Exception {

        ConnectionPool cp = ConnectionPool.getInstance();
        Connection conn = cp.getConnection();

        PreparedStatement ps = null;

        String sql = "DELETE FROM userdb_user WHERE email = ?;";

        boolean deleted = false;

        try {
            ps = conn.prepareStatement(sql);

            ps.setString(1, user.getEmail());

            if (ps.executeUpdate() == 0) {
                deleted = true;
            } else {
                deleted = false;
            }

        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(conn);
        }

        return deleted;
    }

}
