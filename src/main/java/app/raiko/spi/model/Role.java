package app.raiko.spi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

  public static final String TABLE_NAME = "role.roles";
  public static final String ID_COLUMN = TABLE_NAME.concat(".id");
  public static final String NAME_COLUMN = TABLE_NAME.concat(".name");

  public static final String ID_COLUMN_RAW = "id";
  public static final String NAME_COLUMN_RAW = "name";

  private int id;
  private String name;

  public static class AdminRole {

    private AdminRole() {}

    public static final String TABLE_NAME = "role.admins_roles";
    public static final String ADMIN_ID_COLUMN = TABLE_NAME.concat(".admin_id");
    public static final String ROLE_ID_COLUMN = TABLE_NAME.concat(".role_id");
  }

  public static class VetRole {

    private VetRole() {}

    public static final String TABLE_NAME = "role.vets_roles";
    public static final String ADMIN_ID_COLUMN = TABLE_NAME.concat(".vet_id");
    public static final String ROLE_ID_COLUMN = TABLE_NAME.concat(".role_id");
  }

  public static class UserRole {

    private UserRole() {}

    public static final String TABLE_NAME = "role.users_roles";
    public static final String ADMIN_ID_COLUMN = TABLE_NAME.concat(".user_id");
    public static final String ROLE_ID_COLUMN = TABLE_NAME.concat(".role_id");
  }
}
