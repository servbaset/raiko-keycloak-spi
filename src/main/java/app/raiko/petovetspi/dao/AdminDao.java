package app.raiko.petovetspi.dao;

import app.raiko.petovetspi.config.DatabaseConfiguration;
import app.raiko.petovetspi.model.Admin;
import app.raiko.petovetspi.model.Role;
import lombok.AllArgsConstructor;
import org.keycloak.component.ComponentModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;


@AllArgsConstructor
public class AdminDao {

    private final RoleDao roleDao;
    private final ComponentModel config;

    public Admin getAdminByUsername(String username) {
        Admin admin = null;
        try (var connection = DatabaseConfiguration.getConnection(config)) {
            var adminQuery = getAdminByUsernameQuery();
            try (var findAdminStatement = connection.prepareStatement(adminQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                findAdminStatement.setString(1, username);
                var findAdminResult = findAdminStatement.executeQuery();
                var existAdmin = findAdminResult.first();
                if (existAdmin)
                    admin = getAdminObject(findAdminResult);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return admin;
        }
        return admin;
    }

    private Admin getAdminObject(ResultSet findAdminResult) throws SQLException {

        var id = findAdminResult.getLong(Admin.ID_COLUMN_RAW);
        Set<Role> roles = roleDao.findAdminRolesByAdminId(id);

        return Admin.builder()
                .id(id)
                .firstName(findAdminResult.getString(Admin.FIRST_NAME_COLUMN_RAW))
                .lastName(findAdminResult.getString(Admin.LAST_NAME_COLUMN_RAW))
                .username(findAdminResult.getString(Admin.USERNAME_COLUMN_RAW))
                .password(findAdminResult.getString(Admin.PASSWORD_COLUMN_RAW))
                .enabled(findAdminResult.getBoolean(Admin.ENABLED_COLUMN_RAW))
                .roles(roles)
                .build();
    }

    private String getAdminByUsernameQuery() {
        return String.format(
                "select %s, %s ,%S , %s , %s ,%s from %s where %s=?",
                Admin.ID_COLUMN,
                Admin.USERNAME_COLUMN,
                Admin.PASSWORD_COLUMN,
                Admin.FIRST_NAME_COLUMN,
                Admin.LAST_NAME_COLUMN,
                Admin.ENABLED_COLUMN,
                Admin.TABLE_NAME,
                Admin.USERNAME_COLUMN
        );

    }

    private String getPasswordByUsernameQuery() {
        return String.format(
                "select %s from %s where %s=?",
                Admin.PASSWORD_COLUMN,
                Admin.TABLE_NAME,
                Admin.USERNAME_COLUMN
        );

    }

    public String getPasswordByUsername(String username) {
        String password = null;
        try (var connection = DatabaseConfiguration.getConnection(config)) {
            var passwordQuery = getPasswordByUsernameQuery();
            try (var findPasswordStatement = connection.prepareStatement(passwordQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                findPasswordStatement.setString(1, username);
                var findPasswordResult = findPasswordStatement.executeQuery();
                var existPassword = findPasswordResult.first();
                if (existPassword)
                    password = getPasswordObject(findPasswordResult);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return password;
        }
        return password;
    }

    private String getPasswordObject(ResultSet findUserResult) throws SQLException {
        return findUserResult.getString(Admin.PASSWORD_COLUMN_RAW);
    }
}
