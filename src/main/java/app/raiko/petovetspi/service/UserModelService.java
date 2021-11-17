package app.raiko.petovetspi.service;

import app.raiko.petovetspi.dao.AdminDao;
import app.raiko.petovetspi.dao.UserDao;
import app.raiko.petovetspi.dao.VetDao;
import app.raiko.petovetspi.mapper.UserKeycloakModelMapper;
import app.raiko.petovetspi.model.Admin;
import app.raiko.petovetspi.model.User;
import app.raiko.petovetspi.model.UserKeycloakModel;
import app.raiko.petovetspi.model.Vet;
import lombok.AllArgsConstructor;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;

@AllArgsConstructor
public class UserModelService {

    private static final String VET_REALM = "petovet-vet-panel";
    private static final String ADMIN_REALM = "petovet-admin-panel";
    private static final String USER_REALM = "petovet-user";

    private final UserDao userDao;
    private final AdminDao adminDao;
    private final VetDao vetDao;

    private final UserKeycloakModelMapper userKeycloakModelMapper;

    public UserKeycloakModel findUserWithUsername(
            String username,
            RealmModel realm,
            KeycloakSession session,
            ComponentModel componentModel
    ) {
        return getUserModelByUsernameAndRealm(
                username,
                realm,
                session,
                componentModel
        );
    }

    private UserKeycloakModel getUserModelByUsernameAndRealm(
            String username,
            RealmModel realm,
            KeycloakSession session,
            ComponentModel componentModel

    ) {
        String realmName = realm != null ? realm.getName() : "";

        switch (realmName) {
            case USER_REALM:
                var user = getUserByUsername(username);
                return userKeycloakModelMapper.mapToUserKeycloakModel(
                        session,
                        realm,
                        componentModel,
                        user
                );
            case VET_REALM:
                var vet = getVetByUsername(username);
                return userKeycloakModelMapper.mapToUserKeycloakModel(
                        session,
                        realm,
                        componentModel,
                        vet
                );
            case ADMIN_REALM:
                var admin = getAdminByUsername(username);
                return userKeycloakModelMapper.mapToUserKeycloakModel(
                        session,
                        realm,
                        componentModel,
                        admin
                );
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

    private Vet getVetByUsername(String username) {
        return vetDao.getVetByusername(username);
    }

    public String getPasswordByUsername(
            String username,
            RealmModel realm
    ) {
        return getPasswordByUsernameAndRealm(
                username,
                realm
        );
    }

    private String getPasswordByUsernameAndRealm(String username, RealmModel realm) {
        String realmName = realm != null ? realm.getName() : "";
        switch (realmName) {
            case USER_REALM:
                return getUserPasswordByUsername(username);
            case VET_REALM:
                return getVetPasswordByUsername(username);
            case ADMIN_REALM:
                return getAdminPasswordByUsername(username);
            default:
                return null;
        }
    }

    private String getAdminPasswordByUsername(String username) {
        return adminDao.getPasswordByUsername(username);
    }

    private String getVetPasswordByUsername(String username) {
        return vetDao.getPasswordByUsername(username);
    }

    private String getUserPasswordByUsername(String username) {
        return userDao.getPasswordByUsername(username);

    }


}
