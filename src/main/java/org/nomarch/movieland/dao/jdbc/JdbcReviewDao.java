package org.nomarch.movieland.dao.jdbc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.dao.ReviewDao;
import org.nomarch.movieland.dao.jdbc.mapper.ReviewRowMapper;
import org.nomarch.movieland.entity.Review;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JdbcReviewDao implements ReviewDao {

    private static final ReviewRowMapper REVIEW_ROW_MAPPER = new ReviewRowMapper();
    private static final String SAVE_NEW = "INSERT INTO reviews (movie_id, user_id, text) VALUES(:movie_id, :user_id, :text);";
    private static final String GET_BY_MOVIE_ID = "SELECT reviews.id, user_id, text FROM reviews WHERE movie_id = :movie_id";

    private final NamedParameterJdbcTemplate namedParamJdbcTemplate;

    @Override
    public void save(Review review) {
        log.debug("Add new review to DB: {}", review);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("movie_id", review.getMovieId());
        parameterSource.addValue("user_id", review.getUser().getId());
        parameterSource.addValue("text", review.getText());

        namedParamJdbcTemplate.update(SAVE_NEW, parameterSource);
    }

    @Override
    public List<Review> findByMovieId(Long movieId) {
        log.debug("Getting a review by movie id: {}", movieId);

        return namedParamJdbcTemplate.query(GET_BY_MOVIE_ID,
                new MapSqlParameterSource("movie_id", movieId), REVIEW_ROW_MAPPER);
    }
}
