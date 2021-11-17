package app.raiko.spi.factory;

import app.raiko.spi.dao.AdminDao;
import app.raiko.spi.dao.RoleDao;
import app.raiko.spi.dao.UserDao;
import app.raiko.spi.mapper.UserKeycloakModelMapperImpl;
import app.raiko.spi.service.UserModelService;
import org.keycloak.component.ComponentModel;

public class UserModelServiceFactory {

  private UserModelServiceFactory() {}

  public static UserModelService create(ComponentModel config) {
    var roleDao = new RoleDao(config);
    var adminDao = new AdminDao(roleDao, config);
    var userDao = new UserDao(roleDao, config);
    var userKeycloakModelMapper = new UserKeycloakModelMapperImpl();

    return new UserModelService(userDao, adminDao, userKeycloakModelMapper);
  }
}
