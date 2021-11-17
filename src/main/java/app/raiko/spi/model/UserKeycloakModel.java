package app.raiko.spi.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = false)
@ToString
public class UserKeycloakModel extends AbstractUserAdapterFederatedStorage {

  private String username;
  private final String password;
  private final String firstName;
  private final String lastName;
  private final boolean enabled;
  private final Set<Role> roles;

  public UserKeycloakModel(
      KeycloakSession session,
      RealmModel realm,
      ComponentModel storageProviderModel,
      String username,
      String password,
      boolean enabled,
      String firstName,
      String lastName,
      Set<Role> roles) {
    super(session, realm, storageProviderModel);
    this.username = username;
    this.lastName = lastName;
    this.firstName = firstName;
    this.password = password;
    this.enabled = enabled;
    this.roles = roles;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public boolean isEnabled() {
    return this.enabled;
  }

  @Override
  public String getFirstName() {
    return this.firstName;
  }

  @Override
  public String getLastName() {
    return this.lastName;
  }

  @Override
  public Set<RoleModel> getRoleMappings() {

    if (roles == null || realm == null) return Collections.emptySet();

    return roles.stream()
        .filter(role -> realm.getRole(role.getName()) != null)
        .map(role -> realm.getRole(role.getName()))
        .collect(Collectors.toSet());
  }

  public void setSession(KeycloakSession session) {
    super.session = session;
  }

  public void setRealm(RealmModel realm) {
    super.realm = realm;
  }

  public void setComponentModel(ComponentModel storageProviderModel) {
    super.storageProviderModel = storageProviderModel;
  }

  public String getPassword() {
    return this.password;
  }
}
