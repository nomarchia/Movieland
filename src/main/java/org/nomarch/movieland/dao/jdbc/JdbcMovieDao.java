package org.nomarch.movieland.dao.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.dao.MovieDao;
import org.nomarch.movieland.dao.jdbc.mapper.MovieRowMapper;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.web.util.SortingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class JdbcMovieDao implements MovieDao {
    private static final MovieRowMapper MOVIE_ROW_MAPPER = new MovieRowMapper();
    private static final String GET_ALL = "SELECT id, name_native, name_russian, country, year, rating, price, poster_img " +
            "FROM public.movies";
    public static final String GET_RANDOM = "SELECT id, name_native, name_russian, country, year, rating, price, poster_img " +
            "FROM public.movies ORDER BY RANDOM() LIMIT ";
    public static final String GET_MOVIES_BY_GENRE = "SELECT id, name_native, name_russian, country, year, rating, price, poster_img " +
            "FROM public.movies LEFT JOIN public.movie_to_genre ON (public.movies.id = public.movie_to_genre.movie_id) WHERE public.movie_to_genre.genre_id = ?";


    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Integer randomMoviesLimit;

    @Override
    public List<Movie> getAllMovies(SortingUtil sortingUtil) {
        log.debug("Get list of all movies from DB");
        String query = sortingUtil.appendSortingOrder(GET_ALL);
        return jdbcTemplate.query(query, MOVIE_ROW_MAPPER);
    }

    @Override
    public List<Movie> getRandomMovies() {
        log.debug("Get 3 random movies from DB");
        return jdbcTemplate.query(GET_RANDOM + randomMoviesLimit, MOVIE_ROW_MAPPER);
    }

    @Override
    public List<Movie> getMoviesByGenre(Integer genreId, SortingUtil sortingUtil) {
        log.debug("Get list of movies by genre if from DB");
        String query = sortingUtil.appendSortingOrder(GET_MOVIES_BY_GENRE);
        return jdbcTemplate.query(query, new Object[] {genreId}, MOVIE_ROW_MAPPER);
    }
}
