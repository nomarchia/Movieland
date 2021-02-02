package org.nomarch.movieland.dao.jdbc.mapper;

import org.nomarch.movieland.entity.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieRowMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Movie.builder()
                .id(resultSet.getLong("id"))
                .nameNative(resultSet.getString("name_native"))
                .nameRussian(resultSet.getString("name_russian"))
                .yearOfRelease(resultSet.getInt("year"))
                .rating(resultSet.getDouble("rating"))
                .price(resultSet.getDouble("price"))
                .picturePath(resultSet.getString("poster_img"))
                .build();
    }
}
