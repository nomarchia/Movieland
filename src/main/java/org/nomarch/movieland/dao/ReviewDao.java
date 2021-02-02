package org.nomarch.movieland.dao;

import org.nomarch.movieland.entity.Review;

import java.util.List;

public interface ReviewDao {
    void save(Review review);

    List<Review> findByMovieId(Long movieId);
}
