package app.raiko.spi.mapper;

import app.raiko.spi.model.Admin;
import app.raiko.spi.model.User;
import app.raiko.spi.model.UserKeycloakModel;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.mapstruct.Mapper;

@Mapper
public interface UserKeycloakModelMapper {

  default UserKeycloakModel mapToUserKeycloakModel(
      KeycloakSession session, RealmModel realm, ComponentModel storageProviderModel, Admin admin) {

    var userKeycloakModel = mapToUserKeycloakModel(admin);

    userKeycloakModel.setRealm(realm);
    userKeycloakModel.setSession(session);
    userKeycloakModel.setComponentModel(storageProviderModel);

    return userKeycloakModel;
  }

  default UserKeycloakModel mapToUserKeycloakModel(
      KeycloakSession session, RealmModel realm, ComponentModel storageProviderModel, User user) {

    var userKeycloakModel = mapToUserKeycloakModel(user);

    userKeycloakModel.setRealm(realm);
    userKeycloakModel.setSession(session);
    userKeycloakModel.setComponentModel(storageProviderModel);

    return userKeycloakModel;
  }

  UserKeycloakModel mapToUserKeycloakModel(Admin admin);

  UserKeycloakModel mapToUserKeycloakModel(User user);
}
