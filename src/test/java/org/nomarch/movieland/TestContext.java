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
    DataSource createDataSource() {
        PostgreSQLContainer POSTGRESQL_CONTAINER =
                new PostgreSQLContainer (imageName);
        POSTGRESQL_CONTAINER.start();
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(POSTGRESQL_CONTAINER.getJdbcUrl());
        dataSource.setUser(POSTGRESQL_CONTAINER.getUsername());
        dataSource.setPassword(POSTGRESQL_CONTAINER.getPassword());

        Flyway flyway = Flyway.configure().dataSource(dataSource).locations(migrationSchemaLocation).load();
        flyway.migrate();
        return dataSource;
    }

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
