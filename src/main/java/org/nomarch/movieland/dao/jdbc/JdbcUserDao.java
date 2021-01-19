package org.nomarch.movieland.dao.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.dao.UserDao;
import org.nomarch.movieland.dao.jdbc.mapper.UserFullNameRowMapper;
import org.nomarch.movieland.exception.IncorrectCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class JdbcUserDao implements UserDao {
    private static final UserFullNameRowMapper USER_FULL_NAME_ROW_MAPPER = new UserFullNameRowMapper();
    private static final String LOGIN = "SELECT full_name FROM users WHERE email = ? AND password = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String login(String email, String password) {
        log.debug("Logging in user with email: {}", email);

        try {
            return jdbcTemplate.queryForObject(LOGIN, USER_FULL_NAME_ROW_MAPPER, email, password);
        } catch (DataAccessException e) {
            throw new IncorrectCredentialsException("Incorrect email or password for user with email: " + email + "Exception: " + e);
        }
    }
}
