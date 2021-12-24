package app.raiko.spi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
  public static final String TABLE_NAME = "customer.users";
  public static final String ID_COLUMN = TABLE_NAME.concat(".id");
  public static final String USERNAME_COLUMN = TABLE_NAME.concat(".username");
  public static final String FIRST_NAME_COLUMN = TABLE_NAME.concat(".name");
  public static final String LAST_NAME_COLUMN = TABLE_NAME.concat(".last_name");
  public static final String PASSWORD_COLUMN = TABLE_NAME.concat(".password");
  public static final String ENABLED_COLUMN = TABLE_NAME.concat(".enabled");

  public static final String ID_COLUMN_RAW = "id";
  public static final String USERNAME_COLUMN_RAW = "username";
  public static final String FIRST_NAME_COLUMN_RAW = "name";
  public static final String LAST_NAME_COLUMN_RAW = "last_name";
  public static final String PASSWORD_COLUMN_RAW = "password";
  public static final String ENABLED_COLUMN_RAW = "enabled";

  private long id;

  private String username;

  private String firstName;

  private String lastName;
  private String password;
  private boolean enabled;

  private Set<Role> roles;
}
