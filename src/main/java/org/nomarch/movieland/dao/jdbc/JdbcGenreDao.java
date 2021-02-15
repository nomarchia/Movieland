package org.nomarch.movieland.dao.jdbc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.dao.GenreDao;
import org.nomarch.movieland.dao.jdbc.mapper.GenreRowMapper;
import org.nomarch.movieland.entity.Genre;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JdbcGenreDao implements GenreDao {
    private static final GenreRowMapper GENRE_ROW_MAPPER = new GenreRowMapper();
    private static final String FIND_ALL = "SELECT id, name FROM genres";
    private static final String FIND_BY_MOVIE_ID = "SELECT genres.id, name FROM genres LEFT JOIN movie_to_genre " +
            "ON (genres.id = movie_to_genre.genre_id) WHERE movie_id = :movie_id";

    private final NamedParameterJdbcTemplate namedParamJdbcTemplate;

    @Override
    public List<Genre> findAll() {
        log.debug("Getting all genres from DB");
        return namedParamJdbcTemplate.query(FIND_ALL, GENRE_ROW_MAPPER);
    }

    @Override
    public List<Genre> findByMovieId(Long movieId) {
        log.debug("Get genres for movie with id {}", movieId);
        return namedParamJdbcTemplate.query(FIND_BY_MOVIE_ID,
                new MapSqlParameterSource("movie_id", movieId), GENRE_ROW_MAPPER);
    }
}
