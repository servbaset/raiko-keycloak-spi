package app.raiko.petovetspi.dao;

import app.raiko.petovetspi.config.DatabaseConfiguration;
import app.raiko.petovetspi.model.Role;
import lombok.RequiredArgsConstructor;
import org.keycloak.component.ComponentModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class RoleDao {

    private final ComponentModel config;

    public Set<Role> findAdminRolesByAdminId(long adminId) {
        var roleQuery = getFindRolesQuery(Role.AdminRole.TABLE_NAME, Role.AdminRole.ROLE_ID_COLUMN, Role.AdminRole.ADMIN_ID_COLUMN);
        return getRoles(roleQuery, adminId);
    }

    private Set<Role> getRoles(String roleQuery, long id) {
        Set<Role> roles = new HashSet<>();
        try (var connection = DatabaseConfiguration.getConnection(config)) {
            try (var findRoleStatement = connection.prepareStatement(roleQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                findRoleStatement.setLong(1, id);
                var rolesResult = findRoleStatement.executeQuery();
                while (rolesResult.next()) {
                    var roleId = rolesResult.getInt(Role.ID_COLUMN_RAW);
                    var roleName = rolesResult.getString(Role.NAME_COLUMN_RAW);
                    var role = Role.builder()
                            .id(roleId)
                            .name(roleName)
                            .build();
                    roles.add(role);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return roles;
        }
        return roles;
    }

    private String getFindRolesQuery(String tableName, String joinConditon, String whereCondition) {

        return String.format(
                "select %s , %s from %s inner join %s on %s = %s  where %s = ?",
                Role.ID_COLUMN,
                Role.NAME_COLUMN,
                Role.TABLE_NAME,
                tableName,
                Role.ID_COLUMN,
                joinConditon,
                whereCondition
        );

    }

    public Set<Role> findUserRolesByUserId(long userId) {
        var roleQuery = getFindRolesQuery(Role.UserRole.TABLE_NAME, Role.UserRole.ROLE_ID_COLUMN, Role.UserRole.ADMIN_ID_COLUMN);
        return getRoles(roleQuery, userId);
    }

    public Set<Role> findVetRolesByVetId(long vetId) {
        var roleQuery = getFindRolesQuery(Role.VetRole.TABLE_NAME, Role.VetRole.ROLE_ID_COLUMN, Role.VetRole.ADMIN_ID_COLUMN);
        return getRoles(roleQuery, vetId);
    }

}
