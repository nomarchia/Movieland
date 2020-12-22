package org.nomarch.movieland.dao.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.dao.MovieDao;
import org.nomarch.movieland.dao.jdbc.mapper.FullMovieRowMapper;
import org.nomarch.movieland.dao.jdbc.mapper.MovieRowMapper;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.entity.MovieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class JdbcMovieDao implements MovieDao {
    private static final MovieRowMapper MOVIE_ROW_MAPPER = new MovieRowMapper();
    private static final FullMovieRowMapper FULL_MOVIE_ROW_MAPPER = new FullMovieRowMapper();
    private static final String GET_ALL = "SELECT id, name_native, name_russian, country, year, rating, price, poster_img " +
            "FROM public.movies";
    private static final String GET_RANDOM = "SELECT id, name_native, name_russian, country, year, rating, price, poster_img " +
            "FROM public.movies ORDER BY RANDOM() LIMIT ";
    private static final String GET_BY_GENRE = "SELECT id, name_native, name_russian, country, year, rating, price, poster_img " +
            "FROM public.movies LEFT JOIN public.movie_to_genre ON (public.movies.id = public.movie_to_genre.movie_id) " +
            "WHERE public.movie_to_genre.genre_id = ?";
    private static final String GET_BY_ID = "SELECT id, name_native, name_russian, country, year, description, rating, price, poster_img " +
            "FROM public.movies where id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Movie> findAll(MovieRequest movieRequest) {
        log.debug("Get list of all movies from DB");
        String query = movieRequest.appendSortingOrder(GET_ALL);
        return jdbcTemplate.query(query, MOVIE_ROW_MAPPER);
    }

    @Override
    public List<Movie> findRandom(Integer moviesAmount) {
        log.debug("Get " + moviesAmount + " random movies from DB");
        return jdbcTemplate.query(GET_RANDOM + moviesAmount, MOVIE_ROW_MAPPER);
    }

    @Override
    public List<Movie> findByGenre(Integer genreId, MovieRequest movieRequest) {
        log.debug("Get list of movies by genre if from DB");
        String query = movieRequest.appendSortingOrder(GET_BY_GENRE);
        return jdbcTemplate.query(query, MOVIE_ROW_MAPPER, genreId);
    }

    @Override
    public Movie findById(Integer movieId) {
        return jdbcTemplate.queryForObject(GET_BY_ID, FULL_MOVIE_ROW_MAPPER, movieId);
    }
}
