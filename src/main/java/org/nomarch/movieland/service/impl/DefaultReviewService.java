package org.nomarch.movieland.service.impl;

import org.nomarch.movieland.dao.ReviewDao;
import org.nomarch.movieland.entity.Review;
import org.nomarch.movieland.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultReviewService implements ReviewService {
    @Autowired
    private ReviewDao reviewDao;

    @Override
    public void save(Review review) {
        reviewDao.save(review);
    }
}
