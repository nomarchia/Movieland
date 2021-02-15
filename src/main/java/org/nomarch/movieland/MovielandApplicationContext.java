package org.nomarch.movieland;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("org.nomarch.movieland")
@EnableScheduling
public class MovielandApplicationContext {
    @Bean
    protected DataSource dataSource(@Value("${jdbc.user}") String user, @Value("${jdbc.password}") String password,
                                    @Value("${jdbc.url}") String url, @Value("${jdbc.driver}") String driver) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setUsername(user);
        hikariDataSource.setPassword(password);
        hikariDataSource.setDriverClassName(driver);

        return hikariDataSource;
    }

    @Bean
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
