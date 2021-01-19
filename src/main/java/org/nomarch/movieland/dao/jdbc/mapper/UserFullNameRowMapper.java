package org.nomarch.movieland.dao.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFullNameRowMapper implements RowMapper<String> {
    @Override
    public String mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getString("full_name");
    }
}
