package app.raiko.spi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.keycloak.component.ComponentModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import static app.raiko.spi.config.DatabaseProperty.*;

public class DatabaseConfiguration {

  private static final HikariConfig hikariConfig = new HikariConfig();
  private static HikariDataSource dataSource;

  private DatabaseConfiguration() {}

  public static Connection getConnection(ComponentModel config) throws SQLException {

    hikariConfig.setJdbcUrl(config.get(CONFIG_KEY_JDBC_URL));
    hikariConfig.setUsername(config.get(CONFIG_KEY_DB_USERNAME));
    hikariConfig.setPassword(config.get(CONFIG_KEY_DB_PASSWORD));
    hikariConfig.setDriverClassName(config.get(CONFIG_KEY_JDBC_DRIVER));
    hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
    hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
    hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

    if (Objects.isNull(dataSource)) dataSource = new HikariDataSource(hikariConfig);

    //    String driverClass = config.get(CONFIG_KEY_JDBC_DRIVER);
    //    try {
    //      Class.forName(driverClass);
    //    } catch (ClassNotFoundException nfe) {
    //      nfe.printStackTrace();
    //    }
    //
    return dataSource.getConnection();
  }
}
