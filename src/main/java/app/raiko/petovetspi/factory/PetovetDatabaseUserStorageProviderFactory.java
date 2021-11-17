package app.raiko.petovetspi.factory;

import app.raiko.petovetspi.config.DatabaseConfiguration;
import app.raiko.petovetspi.provider.PetovetDatabaseUserStorageProvider;
import org.keycloak.component.ComponentModel;
import org.keycloak.component.ComponentValidationException;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;
import org.keycloak.storage.UserStorageProviderFactory;

import java.sql.Connection;
import java.util.List;

import static app.raiko.petovetspi.config.DatabaseProperty.*;

public class PetovetDatabaseUserStorageProviderFactory implements UserStorageProviderFactory<PetovetDatabaseUserStorageProvider> {

    private static final String ID = "petovet-spi";


    protected final List<ProviderConfigProperty> configMetadata;

    public PetovetDatabaseUserStorageProviderFactory() {
        configMetadata = ProviderConfigurationBuilder.create()
                .property()
                .name(CONFIG_KEY_JDBC_DRIVER)
                .label("JDBC Driver Class")
                .type(ProviderConfigProperty.STRING_TYPE)
                .defaultValue("org.postgresql.Driver")
                .helpText("Fully qualified class name of the JDBC driver")
                .add()
                .property()
                .name(CONFIG_KEY_JDBC_URL)
                .type(ProviderConfigProperty.STRING_TYPE)
                .defaultValue("jdbc:postgresql://localhost:5432/petovet")
                .label("DATABASE URL")
                .add()
                .property()
                .name(CONFIG_KEY_DB_USERNAME)
                .type(ProviderConfigProperty.STRING_TYPE)
                .label("DATABASE USERNAME")
                .defaultValue("****")
                .add()
                .property()
                .name(CONFIG_KEY_DB_PASSWORD)
                .type(ProviderConfigProperty.PASSWORD)
                .label("DATABASE PASSWORD")
                .defaultValue("****")
                .add()
                .build();
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configMetadata;
    }

    @Override
    public void validateConfiguration(KeycloakSession session, RealmModel realm, ComponentModel config)
            throws ComponentValidationException {
        try (Connection c = DatabaseConfiguration.getConnection(config)) {
            try (var statement = c.createStatement()) {
                statement.execute(CONFIG_KEY_VALIDATION_QUERY);
            }
        } catch (Exception ex) {
            throw new ComponentValidationException("unable to validate database connection\n" + config.get(CONFIG_KEY_JDBC_URL) + "\n" + ex.getMessage(), ex.getMessage());
        }
    }


    @Override
    public PetovetDatabaseUserStorageProvider create(KeycloakSession session, ComponentModel model) {
        var userModelService = UserModelServiceFactory.create(model);
        return new PetovetDatabaseUserStorageProvider(session, model, userModelService);
    }

    @Override
    public String getId() {
        return ID;
    }


}
