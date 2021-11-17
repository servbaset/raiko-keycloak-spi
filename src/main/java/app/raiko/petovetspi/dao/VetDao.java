package app.raiko.petovetspi.dao;

import app.raiko.petovetspi.config.DatabaseConfiguration;
import app.raiko.petovetspi.model.Role;
import app.raiko.petovetspi.model.Vet;
import lombok.AllArgsConstructor;
import org.keycloak.component.ComponentModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

@AllArgsConstructor
public class VetDao {

    private final RoleDao roleDao;
    private final ComponentModel config;

    public Vet getVetByusername(String username) {

        Vet vet = null;
        try (var connection = DatabaseConfiguration.getConnection(config)) {
            var vetQuery = getVetByUsernameQuery();
            try (var findVetStatement = connection.prepareStatement(vetQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                findVetStatement.setString(1, username);
                var findVetResult = findVetStatement.executeQuery();
                var existVet = findVetResult.first();
                if (existVet)
                    vet = getVetObject(findVetResult);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return vet;
        }
        return vet;
    }

    private Vet getVetObject(ResultSet findUserResult) throws SQLException {

        var id = findUserResult.getLong(Vet.ID_COLUMN_RAW);
        Set<Role> roles = roleDao.findVetRolesByVetId(id);

        return Vet.builder()
                .id(id)
                .firstName(findUserResult.getString(Vet.FIRST_NAME_COLUMN_RAW))
                .lastName(findUserResult.getString(Vet.LAST_NAME_COLUMN_RAW))
                .username(findUserResult.getString(Vet.USERNAME_COLUMN_RAW))
                .password(findUserResult.getString(Vet.PASSWORD_COLUMN_RAW))
                .enabled(findUserResult.getBoolean(Vet.ENABLED_COLUMN_RAW))
                .roles(roles)
                .build();
    }

    private String getVetByUsernameQuery() {
        return String.format(
                "select %s, %s ,%S , %s , %s ,%s from %s where %s=?",
                Vet.ID_COLUMN,
                Vet.USERNAME_COLUMN,
                Vet.PASSWORD_COLUMN,
                Vet.FIRST_NAME_COLUMN,
                Vet.LAST_NAME_COLUMN,
                Vet.ENABLED_COLUMN,
                Vet.TABLE_NAME,
                Vet.USERNAME_COLUMN
        );

    }

    private String getPasswordByUsernameQuery() {
        return String.format(
                "select %s from %s where %s=?",
                Vet.PASSWORD_COLUMN,
                Vet.TABLE_NAME,
                Vet.USERNAME_COLUMN
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
        return findUserResult.getString(Vet.PASSWORD_COLUMN_RAW);
    }
}
