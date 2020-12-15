package org.nomarch.movieland;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
public class TestContext {
    @Value("${postgres.image.name}")
    String imageName;
    @Value("${migration-schema-location}")
    String migrationSchemaLocation;

    @Bean
    @Primary
    protected DataSource createDataSource() {
        PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer (imageName);
        postgreSQLContainer.start();
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(postgreSQLContainer.getJdbcUrl());
        dataSource.setUser(postgreSQLContainer.getUsername());
        dataSource.setPassword(postgreSQLContainer.getPassword());

        Flyway flyway = Flyway.configure().dataSource(dataSource)
                .locations(migrationSchemaLocation).load();
        flyway.migrate();

        return dataSource;
    }

    @Bean
    protected JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
