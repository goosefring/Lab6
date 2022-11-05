package ca.sait.services;

import ca.sait.dataaccess.RoleDB;
import ca.sait.models.Role;
import ca.sait.models.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rehan
 */
public class RoleService {

    private RoleDB roleDB = new RoleDB();

    public List<Role> getAll() throws Exception {
        List<Role> roleArray = this.roleDB.getAll();
        return roleArray;
    }
}
