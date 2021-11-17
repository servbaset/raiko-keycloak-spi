package app.raiko.petovetspi.mapper;

import app.raiko.petovetspi.model.Admin;
import app.raiko.petovetspi.model.User;
import app.raiko.petovetspi.model.UserKeycloakModel;
import app.raiko.petovetspi.model.Vet;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserKeycloakModelMapper {

    default UserKeycloakModel mapToUserKeycloakModel(KeycloakSession session, RealmModel realm, ComponentModel storageProviderModel, Admin admin) {

        var userKeycloakModel = mapToUserKeycloakModel(admin);

        userKeycloakModel.setRealm(realm);
        userKeycloakModel.setSession(session);
        userKeycloakModel.setComponentModel(storageProviderModel);

        return userKeycloakModel;

    }

    default UserKeycloakModel mapToUserKeycloakModel(KeycloakSession session, RealmModel realm, ComponentModel storageProviderModel, Vet vet) {

        var userKeycloakModel = mapToUserKeycloakModel(vet);

        userKeycloakModel.setRealm(realm);
        userKeycloakModel.setSession(session);
        userKeycloakModel.setComponentModel(storageProviderModel);

        return userKeycloakModel;

    }

    default UserKeycloakModel mapToUserKeycloakModel(KeycloakSession session, RealmModel realm, ComponentModel storageProviderModel, User user) {

        var userKeycloakModel = mapToUserKeycloakModel(user);

        userKeycloakModel.setRealm(realm);
        userKeycloakModel.setSession(session);
        userKeycloakModel.setComponentModel(storageProviderModel);

        return userKeycloakModel;

    }

    UserKeycloakModel mapToUserKeycloakModel(Admin admin);

    UserKeycloakModel mapToUserKeycloakModel(Vet vet);

    UserKeycloakModel mapToUserKeycloakModel(User user);

}
