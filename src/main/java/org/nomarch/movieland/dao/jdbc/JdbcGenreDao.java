package org.nomarch.movieland.dao.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.dao.GenreDao;
import org.nomarch.movieland.dao.jdbc.mapper.GenreRowMapper;
import org.nomarch.movieland.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class JdbcGenreDao implements GenreDao {
    private static final GenreRowMapper GENRE_ROW_MAPPER = new GenreRowMapper();
    private static final String GET_ALL = "SELECT id, name FROM public.genres";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getAll() {
        log.debug("Getting all genres from DB");
        return jdbcTemplate.query(GET_ALL, GENRE_ROW_MAPPER);
    }
}
