package org.nomarch.movieland;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.nomarch.movieland.dao.GenreDao;
import org.nomarch.movieland.dao.cache.CachedGenreDao;
import org.nomarch.movieland.dao.jdbc.JdbcGenreDao;
import org.apache.commons.dbcp2.BasicDataSource;
import org.nomarch.movieland.web.json.CurrencyParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("org.nomarch.movieland")
@EnableScheduling
public class RootApplicationContext {
    @Bean
    protected DataSource dataSource(@Value("${jdbc.user}") String user, @Value("${jdbc.password}") String password,
                                    @Value("${jdbc.url}") String url, @Value("${jdbc.driver}") String driver) {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(url);
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(password);
        basicDataSource.setDriverClassName(driver);

        return basicDataSource;
    }

    @Bean
    protected JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);

        return mapper;
    }

    @Bean
    protected GenreDao jdbcGenreDao() {
        return new JdbcGenreDao();
    }

    @Bean
    protected GenreDao cachedGenreDao() {
        return new CachedGenreDao();
    }

    @Bean
    protected CurrencyParser currencyParser() {
        return new CurrencyParser();
    }
}
