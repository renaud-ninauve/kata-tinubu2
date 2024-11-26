package fr.ninauve.renaud.tinubu.insurancepolicies.usecases.extension;

import fr.ninauve.renaud.tinubu.insurancepolicies.InsurancepoliciesApplication;
import lombok.SneakyThrows;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.util.TestSocketUtils;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class UseCasesExtension implements TestInstancePostProcessor, BeforeEachCallback {
    private static final String DB_DIALECT = "org.hibernate.dialect.PostgreSQLDialect";
    private static final String DB_NAME = "insurance";
    private static final String DB_USER_NAME = "paul";
    private static final String DB_USER_PASSWORD = "grey";

    private static final PostgreSQLContainer<?> DB_CONTAINER = new PostgreSQLContainer<>("postgres:16.4")
            .withDatabaseName(DB_NAME)
            .withUsername(DB_USER_NAME)
            .withPassword(DB_USER_PASSWORD);

    private static String insurancePoliciesUri;

    @SneakyThrows
    private static void init() {
        int serverPort = TestSocketUtils.findAvailableTcpPort();
        System.setProperty("server.port", "" + serverPort);

        DB_CONTAINER.start();
        System.setProperty("spring.datasource.url", DB_CONTAINER.getJdbcUrl());
        System.setProperty("spring.datasource.username", DB_USER_NAME);
        System.setProperty("spring.datasource.password", DB_USER_PASSWORD);
        System.setProperty("spring.datasource.driver-class-name", DB_CONTAINER.getDriverClassName());
        System.setProperty("spring.jpa.properties.hibernate.dialect", DB_DIALECT);

        ConfigurableApplicationContext insurancePoliciesContext = SpringApplication.run(InsurancepoliciesApplication.class);

        UseCasesExtension.insurancePoliciesUri = "http://localhost:" + serverPort;
    }

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext extensionContext) throws Exception {
        if (testInstance instanceof UseCase useCase) {
            if (insurancePoliciesUri == null) {
                init();
            }
            useCase.setApplicationBaseUri(insurancePoliciesUri);
        } else {
            throw new IllegalArgumentException("test instance should implement UseCase");
        }
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        java.util.Properties props = new Properties();
        props.setProperty("user", DB_USER_NAME);
        props.setProperty("password", DB_USER_PASSWORD);
        Connection conn = DriverManager.getConnection(DB_CONTAINER.getJdbcUrl(), props);
        Statement statement = conn.createStatement();
        statement.executeUpdate("TRUNCATE TABLE insurance_policies");
    }
}
