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
public class RoleDB {

    public List<Role> getAll() throws Exception {

        List<Role> roleArray = new ArrayList<>();

        ConnectionPool cp = ConnectionPool.getInstance();
        Connection conn = cp.getConnection();

        PreparedStatement ps = null;

        ResultSet rs = null;

        String sql = "SELECT * FROM userdb_role";

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int roleID = rs.getInt(1);
                String roleName = rs.getString(2);

                Role role = new Role(roleID, roleName);

                roleArray.add(role);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(conn);
        }

        return roleArray;
    }

}
