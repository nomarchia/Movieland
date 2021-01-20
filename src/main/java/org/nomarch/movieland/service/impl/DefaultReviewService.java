package org.nomarch.movieland.service.impl;

import org.nomarch.movieland.dao.jdbc.JdbcReviewDao;
import org.nomarch.movieland.entity.Review;
import org.nomarch.movieland.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultReviewService implements ReviewService {
    @Autowired
    private JdbcReviewDao jdbcReviewDao;

    @Override
    public void save(Review review) {
        jdbcReviewDao.save(review);
    }
}
