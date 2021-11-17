package app.raiko.spi.dao;

import app.raiko.spi.config.DatabaseConfiguration;
import app.raiko.spi.model.Role;
import app.raiko.spi.model.User;
import lombok.AllArgsConstructor;
import org.keycloak.component.ComponentModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

@AllArgsConstructor
public class UserDao {

  private final RoleDao roleDao;
  private final ComponentModel config;

  public User getUserByUsername(String username) {
    User user = null;
    try (var connection = DatabaseConfiguration.getConnection(config)) {
      var userQuery = getUserByUsernameQuery();
      try (var findAdminStatement =
          connection.prepareStatement(
              userQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
        findAdminStatement.setString(1, username);
        var findUserResult = findAdminStatement.executeQuery();
        var existUser = findUserResult.first();
        if (existUser) user = getUserObject(findUserResult);
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return user;
    }
    return user;
  }

  private User getUserObject(ResultSet findUserResult) throws SQLException {

    var id = findUserResult.getLong(User.ID_COLUMN_RAW);
    Set<Role> roles = roleDao.findUserRolesByUserId(id);

    return User.builder()
        .id(id)
        .firstName(findUserResult.getString(User.FIRST_NAME_COLUMN_RAW))
        .lastName(findUserResult.getString(User.LAST_NAME_COLUMN_RAW))
        .username(findUserResult.getString(User.USERNAME_COLUMN_RAW))
        .password(findUserResult.getString(User.PASSWORD_COLUMN_RAW))
        .enabled(findUserResult.getBoolean(User.ENABLED_COLUMN_RAW))
        .roles(roles)
        .build();
  }

  private String getUserByUsernameQuery() {
    return String.format(
        "select %s, %s ,%S , %s , %s ,%s from %s where %s=?",
        User.ID_COLUMN,
        User.USERNAME_COLUMN,
        User.PASSWORD_COLUMN,
        User.FIRST_NAME_COLUMN,
        User.LAST_NAME_COLUMN,
        User.ENABLED_COLUMN,
        User.TABLE_NAME,
        User.USERNAME_COLUMN);
  }

  private String getPasswordByUsernameQuery() {
    return String.format(
        "select %s from %s where %s=?",
        User.PASSWORD_COLUMN, User.TABLE_NAME, User.USERNAME_COLUMN);
  }

  public String getPasswordByUsername(String username) {
    String password = null;
    try (var connection = DatabaseConfiguration.getConnection(config)) {
      var passwordQuery = getPasswordByUsernameQuery();
      try (var findPasswordStatement =
          connection.prepareStatement(
              passwordQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
        findPasswordStatement.setString(1, username);
        var findPasswordResult = findPasswordStatement.executeQuery();
        var existPassword = findPasswordResult.first();
        if (existPassword) password = getPasswordObject(findPasswordResult);
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return password;
    }
    return password;
  }

  private String getPasswordObject(ResultSet findUserResult) throws SQLException {
    return findUserResult.getString(User.PASSWORD_COLUMN_RAW);
  }
}
