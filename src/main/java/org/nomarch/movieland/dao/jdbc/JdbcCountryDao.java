package org.nomarch.movieland.dao.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.dao.CountryDao;
import org.nomarch.movieland.dao.jdbc.mapper.CountryRowMapper;
import org.nomarch.movieland.entity.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class JdbcCountryDao implements CountryDao {
    private static final CountryRowMapper COUNTRY_ROW_MAPPER = new CountryRowMapper();
    private static final String FIND_ALL = "SELECT id, name FROM public.countries";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Country> findAll() {
        log.debug("Getting all countries from DB");
        return jdbcTemplate.query(FIND_ALL, COUNTRY_ROW_MAPPER);
    }
}
