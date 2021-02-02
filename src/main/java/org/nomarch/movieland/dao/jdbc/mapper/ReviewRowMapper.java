package org.nomarch.movieland.dao.jdbc.mapper;


import org.nomarch.movieland.entity.Review;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewRowMapper implements RowMapper<Review> {
    @Override
    public Review mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Review.builder()
                .id(resultSet.getLong("id"))
                .text(resultSet.getString("text"))
                .build();
    }
}
