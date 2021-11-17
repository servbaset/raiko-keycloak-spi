package app.raiko.spi.provider;

import app.raiko.spi.service.UserModelService;
import lombok.RequiredArgsConstructor;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class MyHeroDatabaseUserStorageProvider
    implements UserStorageProvider, UserLookupProvider, CredentialInputValidator {

  private final KeycloakSession session;
  private final ComponentModel componentModel;
  private final UserModelService userModelService;

  protected Map<String, UserModel> loadedUsers = new HashMap<>();

  @Override
  public void close() {}

  @Override
  public boolean supportsCredentialType(String credentialType) {
    return PasswordCredentialModel.TYPE.equals(credentialType);
  }

  @Override
  public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
    return supportsCredentialType(credentialType) && getPassword(user, realm) != null;
  }

  @Override
  public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {
    if (!(credentialInput instanceof UserCredentialModel)) return false;
    if (supportsCredentialType(credentialInput.getType())) {
      String password = getPassword(user, realm);
      return password != null && BCrypt.checkpw(credentialInput.getChallengeResponse(), password);
    } else {
      return false;
    }
  }

  @Override
  public UserModel getUserById(String id, RealmModel realm) {
    StorageId storageId = new StorageId(id);
    String username = storageId.getExternalId();
    return getUserByUsername(username, realm);
  }

  @Override
  public UserModel getUserByUsername(String username, RealmModel realm) {
    UserModel adapter = loadedUsers.get(username);
    if (adapter == null) {
      adapter = createAdapter(realm, username);
      loadedUsers.put(username, adapter);
    }
    return adapter;
  }

  @Override
  public UserModel getUserByEmail(String email, RealmModel realm) {
    return null;
  }

  private String getPassword(UserModel user, RealmModel realm) {
    return userModelService.getPasswordByUsername(user.getUsername(), realm);
  }

  protected UserModel createAdapter(RealmModel realm, String username) {
    return userModelService.findUserWithUsername(username, realm, session, componentModel);
  }
}
