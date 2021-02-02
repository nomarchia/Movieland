package org.nomarch.movieland.dao.jdbc.mapper;

import org.nomarch.movieland.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserIDNameRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder().id(resultSet.getLong("id")).nickname(resultSet.getString("full_name")).build();
    }
}
