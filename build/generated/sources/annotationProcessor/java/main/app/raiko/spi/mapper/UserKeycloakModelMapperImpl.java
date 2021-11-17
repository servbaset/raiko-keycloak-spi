package app.raiko.spi.mapper;

import app.raiko.spi.model.Admin;
import app.raiko.spi.model.Role;
import app.raiko.spi.model.User;
import app.raiko.spi.model.UserKeycloakModel;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-17T19:26:45+0330",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 11.0.11 (AdoptOpenJDK)"
)
public class UserKeycloakModelMapperImpl implements UserKeycloakModelMapper {

    @Override
    public UserKeycloakModel mapToUserKeycloakModel(Admin admin) {
        if ( admin == null ) {
            return null;
        }

        KeycloakSession session = null;
        RealmModel realm = null;
        ComponentModel storageProviderModel = null;
        String username = null;
        String password = null;
        boolean enabled = false;
        String firstName = null;
        String lastName = null;
        Set<Role> roles = null;

        UserKeycloakModel userKeycloakModel = new UserKeycloakModel( session, realm, storageProviderModel, username, password, enabled, firstName, lastName, roles );

        return userKeycloakModel;
    }

    @Override
    public UserKeycloakModel mapToUserKeycloakModel(User user) {
        if ( user == null ) {
            return null;
        }

        KeycloakSession session = null;
        RealmModel realm = null;
        ComponentModel storageProviderModel = null;
        String username = null;
        String password = null;
        boolean enabled = false;
        String firstName = null;
        String lastName = null;
        Set<Role> roles = null;

        UserKeycloakModel userKeycloakModel = new UserKeycloakModel( session, realm, storageProviderModel, username, password, enabled, firstName, lastName, roles );

        return userKeycloakModel;
    }
}
