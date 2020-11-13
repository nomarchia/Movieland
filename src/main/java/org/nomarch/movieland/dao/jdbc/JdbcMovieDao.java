package org.nomarch.movieland.dao.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.dao.MovieDao;
import org.nomarch.movieland.dao.jdbc.mapper.MovieRowMapper;
import org.nomarch.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class JdbcMovieDao implements MovieDao {
    private static final MovieRowMapper MOVIE_ROW_MAPPER = new MovieRowMapper();
    private static final String GET_ALL = "SELECT id, name, country, year, description, rating, price, poster_img " +
            "FROM public.movies";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Movie> getAll() {
        return jdbcTemplate.query(GET_ALL, MOVIE_ROW_MAPPER);
    }
}
