package org.nomarch.movieland.dao.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.common.sorting.util.ParamsUtil;
import org.nomarch.movieland.dao.MovieDao;
import org.nomarch.movieland.dao.QueryGenerator;
import org.nomarch.movieland.dao.jdbc.mapper.FullMovieRowMapper;
import org.nomarch.movieland.dao.jdbc.mapper.MovieRowMapper;
import org.nomarch.movieland.dto.movie.MovieDTO;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.dto.movie.MovieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Repository
public class JdbcMovieDao implements MovieDao {
    private static final MovieRowMapper MOVIE_ROW_MAPPER = new MovieRowMapper();
    private static final FullMovieRowMapper FULL_MOVIE_ROW_MAPPER = new FullMovieRowMapper();
    private static final String GET_ALL = "SELECT id, name_native, name_russian, year, rating, price, poster_img " +
            "FROM public.movies";
    private static final String GET_RANDOM = "SELECT id, name_native, name_russian, year, rating, price, poster_img " +
            "FROM public.movies ORDER BY RANDOM() LIMIT ";
    private static final String GET_BY_GENRE = "SELECT id, name_native, name_russian, year, rating, price, poster_img " +
            "FROM public.movies LEFT JOIN public.movie_to_genre ON (public.movies.id = public.movie_to_genre.movie_id) " +
            "WHERE public.movie_to_genre.genre_id = ?";
    private static final String GET_BY_ID = "SELECT id, name_native, name_russian, year, description, rating, price, poster_img " +
            "FROM public.movies where id = ?";
    private static final String ADD_NEW = "INSERT INTO public.movies (name_native, name_russian, year, description, price, poster_img) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE public.movies SET name_native = ?, name_russian = ?, year = ?, description = ?, " +
            "price = ?, poster_img = ? WHERE id = ?";
    private static final String ADD_MOVIE_GENRES = "INSERT INTO public.movie_to_genre (movie_id, genre_id) VALUES (?, ?)";
    private static final String ADD_MOVIE_COUNTRIES = "INSERT INTO public.movie_to_country (movie_id, country_id) VALUES (?, ?)";
    private static final String GET_ID_BY_NAME = "SELECT id FROM public.movies WHERE name_native = '";
    private static final String CLEAR_MOVIE_GENRES = "DELETE FROM public.movie_to_genre WHERE movie_id = ?";
    private static final String CLEAR_MOVIE_COUNTRIES = "DELETE FROM public.movie_to_country WHERE movie_id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    SimpleJdbcInsert simpleJdbcInsert;



    @Override
    public List<Movie> findAll(MovieRequest movieRequest) {
        log.debug("Get list of all movies from DB");
        String query = ParamsUtil.appendSortingOrder(GET_ALL, movieRequest);
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
        String query = ParamsUtil.appendSortingOrder(GET_BY_GENRE, movieRequest);
        return jdbcTemplate.query(query, MOVIE_ROW_MAPPER, genreId);
    }

    @Override
    public Movie findById(Integer movieId) {
        return jdbcTemplate.queryForObject(GET_BY_ID, FULL_MOVIE_ROW_MAPPER, movieId);
    }

    @Override
    public void add(MovieDTO newMovie) {
        jdbcTemplate.update(ADD_NEW, newMovie.getNameNative(), newMovie.getNameRussian(), newMovie.getYearOfRelease(),
                newMovie.getDescription(), newMovie.getPrice(), newMovie.getPicturePath());

        Integer movieId = jdbcTemplate.queryForObject(GET_ID_BY_NAME + newMovie.getNameNative() + "'", Integer.class);
        for (int genreId : newMovie.getGenres()) {
            jdbcTemplate.update(ADD_MOVIE_GENRES, movieId, genreId);
        }

        for (int countryId : newMovie.getCountries()) {
            jdbcTemplate.update(ADD_MOVIE_COUNTRIES, movieId, countryId);
        }
    }

    @Override
    public void edit(Integer movieId, MovieDTO updatedMovie) {
        String updateQuery = QueryGenerator.formMovieUpdateQuery(updatedMovie);
        jdbcTemplate.update(updateQuery, movieId);

        if (updatedMovie.getGenres() != null) {
            jdbcTemplate.update(CLEAR_MOVIE_GENRES, movieId);

            for (int genreId : updatedMovie.getGenres()) {
                jdbcTemplate.update(ADD_MOVIE_GENRES, movieId, genreId);
            }
        }

        if (updatedMovie.getCountries() != null) {
            jdbcTemplate.update(CLEAR_MOVIE_COUNTRIES, movieId);

            for (int countryId : updatedMovie.getCountries()) {
                jdbcTemplate.update(ADD_MOVIE_COUNTRIES, movieId, countryId);
            }
        }
    }
}
