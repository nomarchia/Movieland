package org.nomarch.movieland.service;

import org.nomarch.movieland.dto.review.ReviewReturnedDTO;
import org.nomarch.movieland.entity.Review;

import java.util.List;

public interface ReviewService {
    void save(Review review);

    List<ReviewReturnedDTO> findByMovieId(Long movieId);
}
