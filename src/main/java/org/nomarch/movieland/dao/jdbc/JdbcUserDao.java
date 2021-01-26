package org.nomarch.movieland.dao.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.dao.UserDao;
import org.nomarch.movieland.dao.jdbc.mapper.UserWithoutCredentialsRowMapper;
import org.nomarch.movieland.entity.User;
import org.nomarch.movieland.exception.IncorrectCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class JdbcUserDao implements UserDao {
    private static final UserWithoutCredentialsRowMapper USER_WITHOUT_CREDENTIALS_ROW_MAPPER = new UserWithoutCredentialsRowMapper();
    private static final String LOGIN = "SELECT users.id, full_name, user_role.role FROM users " +
            "INNER JOIN user_role ON users.id = user_role.user_id WHERE email = ? AND password = ? ";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User login(String email, String password) {
        log.debug("Querying from DB for user with email: {}", email);

        try {
            return jdbcTemplate.queryForObject(LOGIN, USER_WITHOUT_CREDENTIALS_ROW_MAPPER, email, password);
        } catch (DataAccessException e) {
            log.debug("User with email {} is not found or email/password was incorrect", email);
            throw new IncorrectCredentialsException("Incorrect email or password for user with email: " + email + ". Exception: " + e);
        }
    }
}
