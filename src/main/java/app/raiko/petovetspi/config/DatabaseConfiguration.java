package app.raiko.petovetspi.config;

import org.keycloak.component.ComponentModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static app.raiko.petovetspi.config.DatabaseProperty.*;

public class DatabaseConfiguration {

    private DatabaseConfiguration() {

    }

    public static Connection getConnection(ComponentModel config) throws SQLException {

        String driverClass = config.get(CONFIG_KEY_JDBC_DRIVER);
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException nfe) {
            nfe.printStackTrace();
        }

        return DriverManager.getConnection(
                config.get(CONFIG_KEY_JDBC_URL),
                config.get(CONFIG_KEY_DB_USERNAME),
                config.get(CONFIG_KEY_DB_PASSWORD));
    }

}
