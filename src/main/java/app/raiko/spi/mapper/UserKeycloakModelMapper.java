package app.raiko.spi.mapper;

import app.raiko.spi.model.Admin;
import app.raiko.spi.model.User;
import app.raiko.spi.model.UserKeycloakModel;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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

  @Mapping(target = "storageProviderModel", ignore = true)
  @Mapping(target = "session", ignore = true)
  @Mapping(target = "serviceAccountClientLink", ignore = true)
  @Mapping(target = "realm", ignore = true)
  @Mapping(target = "federationLink", ignore = true)
  @Mapping(target = "emailVerified", ignore = true)
  @Mapping(target = "email", ignore = true)
  @Mapping(target = "createdTimestamp", ignore = true)
  @Mapping(target = "componentModel", ignore = true)
  @Mapping(target = "firstName", source = "admin.firstName")
  @Mapping(target = "lastName", source = "admin.lastName")
  @Mapping(target = "enabled", source = "admin.enabled")
  @Mapping(target = "username", source = "admin.username")
  @Mapping(target = "password", source = "admin.password")
  @Mapping(target = "roles", source = "admin.roles")
  UserKeycloakModel mapToUserKeycloakModel(Admin admin);

  @Mapping(target = "storageProviderModel", ignore = true)
  @Mapping(target = "session", ignore = true)
  @Mapping(target = "serviceAccountClientLink", ignore = true)
  @Mapping(target = "realm", ignore = true)
  @Mapping(target = "federationLink", ignore = true)
  @Mapping(target = "emailVerified", ignore = true)
  @Mapping(target = "email", ignore = true)
  @Mapping(target = "createdTimestamp", ignore = true)
  @Mapping(target = "componentModel", ignore = true)
  @Mapping(target = "firstName", source = "user.firstName")
  @Mapping(target = "lastName", source = "user.lastName")
  @Mapping(target = "enabled", source = "user.enabled")
  @Mapping(target = "username", source = "user.username")
  @Mapping(target = "password", source = "user.password")
  @Mapping(target = "roles", source = "user.roles")
  UserKeycloakModel mapToUserKeycloakModel(User user);
}
