package app.raiko.spi.mapper;

import app.raiko.spi.model.Admin;
import app.raiko.spi.model.Role;
import app.raiko.spi.model.User;
import app.raiko.spi.model.UserKeycloakModel;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-19T18:04:43+0330",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 11.0.11 (AdoptOpenJDK)"
)
public class UserKeycloakModelMapperImpl implements UserKeycloakModelMapper {

    @Override
    public UserKeycloakModel mapToUserKeycloakModel(Admin admin) {
        if ( admin == null ) {
            return null;
        }

        String firstName = null;
        String lastName = null;
        boolean enabled = false;
        String username = null;
        String password = null;
        Set<Role> roles = null;

        firstName = admin.getFirstName();
        lastName = admin.getLastName();
        enabled = admin.isEnabled();
        username = admin.getUsername();
        password = admin.getPassword();
        Set<Role> set = admin.getRoles();
        if ( set != null ) {
            roles = new HashSet<Role>( set );
        }

        ComponentModel storageProviderModel = null;
        KeycloakSession session = null;
        RealmModel realm = null;

        UserKeycloakModel userKeycloakModel = new UserKeycloakModel( session, realm, storageProviderModel, username, password, enabled, firstName, lastName, roles );

        return userKeycloakModel;
    }

    @Override
    public UserKeycloakModel mapToUserKeycloakModel(User user) {
        if ( user == null ) {
            return null;
        }

        String firstName = null;
        String lastName = null;
        boolean enabled = false;
        String username = null;
        String password = null;
        Set<Role> roles = null;

        firstName = user.getFirstName();
        lastName = user.getLastName();
        enabled = user.isEnabled();
        username = user.getUsername();
        password = user.getPassword();
        Set<Role> set = user.getRoles();
        if ( set != null ) {
            roles = new HashSet<Role>( set );
        }

        ComponentModel storageProviderModel = null;
        KeycloakSession session = null;
        RealmModel realm = null;

        UserKeycloakModel userKeycloakModel = new UserKeycloakModel( session, realm, storageProviderModel, username, password, enabled, firstName, lastName, roles );

        return userKeycloakModel;
    }
}
