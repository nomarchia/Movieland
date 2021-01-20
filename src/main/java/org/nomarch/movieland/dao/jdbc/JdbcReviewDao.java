package org.nomarch.movieland.dao.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.dao.ReviewDao;
import org.nomarch.movieland.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class JdbcReviewDao implements ReviewDao {
    private static final String SAVE_NEW = "INSERT INTO reviews (movie_id, user_id, review) VALUES(?, ?, ?);";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(Review review) {
        log.debug("Add new review to DB: {}", review);
        jdbcTemplate.update(SAVE_NEW, review.getMovieId(), review.getUserId(), review.getText());
    }
}
