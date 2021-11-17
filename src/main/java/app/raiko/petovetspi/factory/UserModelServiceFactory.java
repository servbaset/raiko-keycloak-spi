package app.raiko.petovetspi.factory;

import app.raiko.petovetspi.dao.AdminDao;
import app.raiko.petovetspi.dao.RoleDao;
import app.raiko.petovetspi.dao.UserDao;
import app.raiko.petovetspi.dao.VetDao;
import app.raiko.petovetspi.mapper.UserKeycloakModelMapperImpl;
import app.raiko.petovetspi.service.UserModelService;
import org.keycloak.component.ComponentModel;

public class UserModelServiceFactory {

    private UserModelServiceFactory() {

    }

    public static UserModelService create(ComponentModel config) {
        var roleDao = new RoleDao(config);
        var adminDao = new AdminDao(roleDao, config);
        var userDao = new UserDao(roleDao, config);
        var vetDao = new VetDao(roleDao, config);
        var userKeycloakModelMapper = new UserKeycloakModelMapperImpl();

        return new UserModelService(userDao, adminDao, vetDao, userKeycloakModelMapper);
    }


}
