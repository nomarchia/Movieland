package org.nomarch.movieland.dao;

import org.nomarch.movieland.entity.Movie;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.HashMap;
import java.util.Map;


public class QueryGenerator {

    public static Map<String, Object> formMovieUpdateQuery(Movie updatedMovie) {
        StringBuilder queryBuilder = new StringBuilder("UPDATE movies SET ");
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        if (updatedMovie.getNameNative() != null) {
            queryBuilder.append("name_native = :name_native, ");
            mapSqlParameterSource.addValue("name_native", updatedMovie.getNameNative());
        }
        if (updatedMovie.getNameRussian() != null) {
            queryBuilder.append("name_russian = :name_russian, ");
            mapSqlParameterSource.addValue("name_russian", updatedMovie.getNameRussian());
        }
        if (updatedMovie.getYearOfRelease() != null) {
            queryBuilder.append("year = :year, ");
            mapSqlParameterSource.addValue("year", updatedMovie.getYearOfRelease());
        }
        if (updatedMovie.getDescription() != null) {
            queryBuilder.append("description = :description, ");
            mapSqlParameterSource.addValue("description", updatedMovie.getDescription());
        }
        if (updatedMovie.getPrice() != null) {
            queryBuilder.append("price = :price, ");
            mapSqlParameterSource.addValue("price", updatedMovie.getPrice());
        }
        if (updatedMovie.getPicturePath() != null) {
            queryBuilder.append("poster_img = :poster_img, ");
            mapSqlParameterSource.addValue("poster_img", updatedMovie.getPicturePath());
        }

        queryBuilder.deleteCharAt(queryBuilder.lastIndexOf(","));
        queryBuilder.append("WHERE id = :movie_id");
        mapSqlParameterSource.addValue("movie_id", updatedMovie.getId());

        Map<String, Object> mapWithParams = new HashMap<>();
        mapWithParams.put("query", queryBuilder.toString());
        mapWithParams.put("parameterSource", mapSqlParameterSource);

        return mapWithParams;
    }
}
