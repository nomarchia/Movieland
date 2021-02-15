package org.nomarch.movieland.dao.jdbc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.dao.UserDao;
import org.nomarch.movieland.dao.jdbc.mapper.UserIDNameRowMapper;
import org.nomarch.movieland.dao.jdbc.mapper.UserWithoutCredentialsRowMapper;
import org.nomarch.movieland.entity.User;
import org.nomarch.movieland.exception.IncorrectCredentialsException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JdbcUserDao implements UserDao {

    private static final UserWithoutCredentialsRowMapper USER_WITHOUT_CREDENTIALS_ROW_MAPPER = new UserWithoutCredentialsRowMapper();
    private static final UserIDNameRowMapper USER_ID_NAME_ROW_MAPPER = new UserIDNameRowMapper();
    private static final String LOGIN = "SELECT users.id, full_name, user_role.role FROM users " +
            "INNER JOIN user_role ON users.id = user_role.user_id WHERE email = :email AND password = :password ";
    private static final String FIND_BY_MOVIE_ID = "SELECT users.id, full_name FROM users " +
            "LEFT JOIN reviews ON (users.id = reviews.user_id) WHERE reviews.id = :review_id ";

    private final NamedParameterJdbcTemplate namedParamJdbcTemplate;

    @Override
    public User login(String email, String password) {
        log.debug("Querying from DB for user with email: {}", email);

        try {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource("email", email);
            parameterSource.addValue("password", password);

            return namedParamJdbcTemplate.queryForObject(LOGIN, parameterSource, USER_WITHOUT_CREDENTIALS_ROW_MAPPER);
        } catch (DataAccessException e) {
            log.debug("User with email {} is not found or email/password was incorrect", email);
            throw new IncorrectCredentialsException("Incorrect email or password for user with email: " + email + ". Exception: " + e);
        }
    }

    @Override
    public User findUserByReviewId(Long reviewId) {
        log.debug("Getting a user by review id: {}", reviewId);

        return namedParamJdbcTemplate.queryForObject(FIND_BY_MOVIE_ID,
                new MapSqlParameterSource("review_id", reviewId), USER_ID_NAME_ROW_MAPPER);
    }
}
