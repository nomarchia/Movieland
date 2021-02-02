package org.nomarch.movieland.dao.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.dao.ReviewDao;
import org.nomarch.movieland.dao.jdbc.mapper.ReviewRowMapper;
import org.nomarch.movieland.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class JdbcReviewDao implements ReviewDao {
    private static final ReviewRowMapper REVIEW_ROW_MAPPER = new ReviewRowMapper();
    private static final String SAVE_NEW = "INSERT INTO reviews (movie_id, user_id, text) VALUES(?, ?, ?);";
    private static final String GET_BY_MOVIE_ID = "SELECT reviews.id, user_id, text FROM reviews WHERE movie_id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(Review review) {
        log.debug("Add new review to DB: {}", review);
        jdbcTemplate.update(SAVE_NEW, review.getMovieId(), review.getUser().getId(), review.getText());
    }

    @Override
    public List<Review> findByMovieId(Long movieId) {
        return jdbcTemplate.query(GET_BY_MOVIE_ID, REVIEW_ROW_MAPPER, movieId);
    }
}
