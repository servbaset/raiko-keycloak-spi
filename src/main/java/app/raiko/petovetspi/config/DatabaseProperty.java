package app.raiko.petovetspi.config;

public class DatabaseProperty {

    private DatabaseProperty() {

    }

    public static final String CONFIG_KEY_JDBC_DRIVER = "config-key-jdbc-driver";
    public static final String CONFIG_KEY_JDBC_URL = "config-key-jdbc-url";
    public static final String CONFIG_KEY_DB_USERNAME = "config-key-db-username";
    public static final String CONFIG_KEY_DB_PASSWORD = "config-key-db-password";
    public static final String CONFIG_KEY_VALIDATION_QUERY = "select * from role.roles";

}
