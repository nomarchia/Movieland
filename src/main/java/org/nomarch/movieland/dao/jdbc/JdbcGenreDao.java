package org.nomarch.movieland.dao.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.dao.GenreDao;
import org.nomarch.movieland.dao.jdbc.mapper.GenreRowMapper;
import org.nomarch.movieland.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@Component("jdbcGenreDao")
public class JdbcGenreDao implements GenreDao {
    private static final GenreRowMapper GENRE_ROW_MAPPER = new GenreRowMapper();
    private static final String FIND_ALL = "SELECT id, name FROM genres";
    private static final String FIND_BY_MOVIE_ID = "SELECT genres.id, name FROM genres LEFT JOIN movie_to_genre ON (genres.id = movie_to_genre.genre_id) WHERE movie_id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> findAll() {
        log.debug("Getting all genres from DB");
        return jdbcTemplate.query(FIND_ALL, GENRE_ROW_MAPPER);
    }

    @Override
    public List<Genre> findByMovieId(Long movieId) {
        log.debug("Get genres for movie with id {}", movieId);
        return jdbcTemplate.query(FIND_BY_MOVIE_ID, GENRE_ROW_MAPPER, movieId);
    }
}
