package org.nomarch.movieland.dao.jdbc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.dao.CountryDao;
import org.nomarch.movieland.dao.jdbc.mapper.CountryRowMapper;
import org.nomarch.movieland.entity.Country;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JdbcCountryDao implements CountryDao {
    private static final CountryRowMapper COUNTRY_ROW_MAPPER = new CountryRowMapper();
    private static final String FIND_ALL = "SELECT id, name FROM countries";
    private static final String FIND_BY_MOVIE_ID = "SELECT countries.id, name FROM countries LEFT JOIN movie_to_country " +
            "ON (countries.id = movie_to_country.country_id) WHERE movie_to_country.movie_id = :movie_id";

    private final NamedParameterJdbcTemplate namedParamJdbcTemplate;

    @Override
    public List<Country> findAll() {
        log.debug("Getting all countries from DB");
        return namedParamJdbcTemplate.query(FIND_ALL, COUNTRY_ROW_MAPPER);
    }

    @Override
    public List<Country> findByMovieId(Long movieId) {
        log.debug("Get countries for movie with id {}", movieId);
        return namedParamJdbcTemplate.query(FIND_BY_MOVIE_ID, new MapSqlParameterSource("movie_id", movieId), COUNTRY_ROW_MAPPER);
    }
}
