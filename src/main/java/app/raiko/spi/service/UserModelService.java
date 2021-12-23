package app.raiko.spi.service;

import app.raiko.spi.dao.AdminDao;
import app.raiko.spi.dao.UserDao;
import app.raiko.spi.mapper.UserKeycloakModelMapper;
import app.raiko.spi.model.Admin;
import app.raiko.spi.model.User;
import app.raiko.spi.model.UserKeycloakModel;
import lombok.AllArgsConstructor;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;

@AllArgsConstructor
public class UserModelService {

  private static final String ADMIN_REALM = "admin-myhero";
  private static final String USER_REALM = "user-myhero";

  private final UserDao userDao;
  private final AdminDao adminDao;

  private final UserKeycloakModelMapper userKeycloakModelMapper;

  public UserKeycloakModel findUserWithUsername(
      String username, RealmModel realm, KeycloakSession session, ComponentModel componentModel) {
    return getUserModelByUsernameAndRealm(username, realm, session, componentModel);
  }

  private UserKeycloakModel getUserModelByUsernameAndRealm(
      String username, RealmModel realm, KeycloakSession session, ComponentModel componentModel) {

    String realmName = realm != null ? realm.getName() : "";

    switch (realmName) {
      case USER_REALM:
        var user = getUserByUsername(username);
        return userKeycloakModelMapper.mapToUserKeycloakModel(session, realm, componentModel, user);
      case ADMIN_REALM:
        var admin = getAdminByUsername(username);
        return userKeycloakModelMapper.mapToUserKeycloakModel(
            session, realm, componentModel, admin);
      default:
        return null;
    }
  }

  private User getUserByUsername(String username) {
    return userDao.getUserByUsername(username);
  }

  private Admin getAdminByUsername(String username) {
    return adminDao.getAdminByUsername(username);
  }

  public String getPasswordByUsername(String username, RealmModel realm) {
    return getPasswordByUsernameAndRealm(username, realm);
  }

  private String getPasswordByUsernameAndRealm(String username, RealmModel realm) {
    String realmName = realm != null ? realm.getName() : "";
    switch (realmName) {
      case USER_REALM:
        return getUserPasswordByUsername(username);
      case ADMIN_REALM:
        return getAdminPasswordByUsername(username);
      default:
        return null;
    }
  }

  private String getAdminPasswordByUsername(String username) {
    return adminDao.getPasswordByUsername(username);
  }

  private String getUserPasswordByUsername(String username) {
    return userDao.getPasswordByUsername(username);
  }
}
