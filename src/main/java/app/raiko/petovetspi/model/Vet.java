package app.raiko.petovetspi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Vet {
    public static final String TABLE_NAME = "vet.vets";
    public static final String ID_COLUMN = TABLE_NAME.concat(".id");
    public static final String USERNAME_COLUMN = TABLE_NAME.concat(".user_name");
    public static final String FIRST_NAME_COLUMN = TABLE_NAME.concat(".first_name");
    public static final String LAST_NAME_COLUMN = TABLE_NAME.concat(".last_name");
    public static final String PASSWORD_COLUMN = TABLE_NAME.concat(".password");
    public static final String ENABLED_COLUMN = TABLE_NAME.concat(".enabled");

    public static final String ID_COLUMN_RAW = "id";
    public static final String USERNAME_COLUMN_RAW = "user_name";
    public static final String FIRST_NAME_COLUMN_RAW = "first_name";
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
