package org.nomarch.movieland.dao.jdbc.mapper;

import org.nomarch.movieland.entity.Country;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryRowMapper implements RowMapper<Country> {
    @Override
    public Country mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Country.builder().id(resultSet.getInt("id")).name(resultSet.getString("name")).build();
    }
}
