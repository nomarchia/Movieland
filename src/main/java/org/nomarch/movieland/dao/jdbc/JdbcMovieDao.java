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
    public static final String GET_RANDOM = "SELECT id, name, country, year, description, rating, price, poster_img " +
            "FROM public.movies ORDER BY RANDOM() LIMIT 3";
    public static final String GET_MOVIES_BY_GENRE = "SELECT id, name, country, year, description, rating, price, poster_img " +
            "FROM public.movies INNER JOIN public.movie_to_genre ON (public.movies.id = public.movie_to_genre.movie_id) WHERE public.movie_to_genre.genre_id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Movie> getAll() {
        return jdbcTemplate.query(GET_ALL, MOVIE_ROW_MAPPER);
    }

    @Override
    public List<Movie> getThreeRandomMovies() {
        return jdbcTemplate.query(GET_RANDOM, MOVIE_ROW_MAPPER);
    }

    @Override
    public List<Movie> getMoviesByGenre(Integer genreId) {
//        return jdbcTemplate.query(GET_MOVIES_BY_GENRE, MOVIE_ROW_MAPPER);
        return jdbcTemplate.query(GET_MOVIES_BY_GENRE, new Object[] {genreId}, MOVIE_ROW_MAPPER);
    }
}
