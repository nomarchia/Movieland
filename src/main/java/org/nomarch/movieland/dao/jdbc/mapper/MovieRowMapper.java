package org.nomarch.movieland.dao.jdbc.mapper;

import org.nomarch.movieland.entity.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieRowMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Movie.builder()
                .id(resultSet.getInt("id"))
                .nameNative(resultSet.getString("name_native"))
                .nameRussian(resultSet.getString("name_russian"))
                .country(resultSet.getString("country"))
                .year(resultSet.getInt("year"))
//                .description(resultSet.getString("description"))
                .rating(resultSet.getDouble("rating"))
                .price(resultSet.getDouble("price"))
                .posterImg(resultSet.getString("poster_img"))
                .build();
    }
}
