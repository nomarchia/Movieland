package org.nomarch.movieland.dao.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.common.sorting.util.ParamsUtil;
import org.nomarch.movieland.dao.MovieDao;
import org.nomarch.movieland.dao.QueryGenerator;
import org.nomarch.movieland.dao.jdbc.mapper.FullMovieRowMapper;
import org.nomarch.movieland.dao.jdbc.mapper.MovieRowMapper;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.dto.movie.MovieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class JdbcMovieDao implements MovieDao {
    private static final MovieRowMapper MOVIE_ROW_MAPPER = new MovieRowMapper();
    private static final FullMovieRowMapper FULL_MOVIE_ROW_MAPPER = new FullMovieRowMapper();
    private static final String GET_ALL = "SELECT id, name_native, name_russian, year, rating, price, poster_img FROM movies";
    private static final String GET_RANDOM = "SELECT id, name_native, name_russian, year, rating, price, poster_img " +
            "FROM movies ORDER BY RANDOM() LIMIT ";
    private static final String GET_BY_GENRE = "SELECT id, name_native, name_russian, year, rating, price, poster_img " +
            "FROM movies LEFT JOIN movie_to_genre ON (movies.id = movie_to_genre.movie_id) " +
            "WHERE movie_to_genre.genre_id = :genre_id";
    private static final String GET_BY_ID = "SELECT id, name_native, name_russian, year, description, rating, price, poster_img " +
            "FROM movies where id = :movie_id";
    private static final String ADD_NEW = "INSERT INTO movies (name_native, name_russian, year, description, price, poster_img) " +
            "VALUES (:name_native, :name_russian, :year, :description, :price, :poster_img)";
    private static final String ADD_MOVIE_GENRES = "INSERT INTO movie_to_genre (movie_id, genre_id) VALUES (?, ?)";
    private static final String ADD_MOVIE_COUNTRIES = "INSERT INTO movie_to_country (movie_id, country_id) VALUES (?, ?)";
    private static final String CLEAR_MOVIE_GENRES = "DELETE FROM movie_to_genre WHERE movie_id = :movie_id";
    private static final String CLEAR_MOVIE_COUNTRIES = "DELETE FROM movie_to_country WHERE movie_id = :movie_id";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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

        return namedParameterJdbcTemplate.query(query, new MapSqlParameterSource("genre_id", genreId), MOVIE_ROW_MAPPER);
    }

    @Override
    public Movie findById(Long movieId) {
        log.debug("Getting a movie with id {} from DB", movieId);
        return namedParameterJdbcTemplate.queryForObject(GET_BY_ID, new MapSqlParameterSource("movie_id", movieId), FULL_MOVIE_ROW_MAPPER);
    }

    @Override
    public void add(Movie newMovie) {
        log.debug("Saving new movie ({}) to DB", newMovie);

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("name_native", newMovie.getNameNative());
        map.addValue("name_russian", newMovie.getNameRussian());
        map.addValue("year", newMovie.getYearOfRelease());
        map.addValue("description", newMovie.getDescription());
        map.addValue("price", newMovie.getPrice());
        map.addValue("poster_img", newMovie.getPicturePath());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(ADD_NEW, map, keyHolder);
        Number movieIdTemp = (Number) keyHolder.getKeys().get("id");
        Long movieId = movieIdTemp.longValue();

        updateManyToManyTable(ADD_MOVIE_GENRES, movieId, newMovie.getGenres());
        updateManyToManyTable(ADD_MOVIE_COUNTRIES, movieId, newMovie.getCountries());
    }

    @Override
    public void edit(Movie updatedMovie) {
        log.debug("Updating a movie by id {} with new values: {}", updatedMovie.getId(), updatedMovie);

        Map<String, Object> mapWithPreparedQueryAndParams = QueryGenerator.formMovieUpdateQuery(updatedMovie);
        String updateQuery = (String) mapWithPreparedQueryAndParams.get("query");
        MapSqlParameterSource parameterSource = (MapSqlParameterSource) mapWithPreparedQueryAndParams.get("parameterSource");
        namedParameterJdbcTemplate.update(updateQuery, parameterSource);

        if (updatedMovie.getGenres() != null) {
            clearOldValues(CLEAR_MOVIE_GENRES, updatedMovie.getId());
            updateManyToManyTable(ADD_MOVIE_GENRES, updatedMovie.getId(), updatedMovie.getGenres());
        }

        if (updatedMovie.getCountries() != null) {
            clearOldValues(CLEAR_MOVIE_COUNTRIES, updatedMovie.getId());
            updateManyToManyTable(ADD_MOVIE_COUNTRIES, updatedMovie.getId(), updatedMovie.getCountries());
        }
    }

    private void updateManyToManyTable(String insertQuery, Long movieId, int[] valueIds) {
        log.debug("Inserting values by ids {} for movie with id {}, using query ({})", valueIds, movieId, insertQuery);
        for (int valueId : valueIds) {
            jdbcTemplate.update(insertQuery, movieId, valueId);
        }
    }

    private void clearOldValues(String deleteQuery, Long movieId) {
        log.debug("Clearing old values for movie with id {}, using ({}) query", movieId, deleteQuery);
        namedParameterJdbcTemplate.update(deleteQuery, new MapSqlParameterSource("movie_id", movieId));
    }
}
