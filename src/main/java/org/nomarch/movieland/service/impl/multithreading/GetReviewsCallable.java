package org.nomarch.movieland.service.impl.multithreading;

import lombok.RequiredArgsConstructor;
import org.nomarch.movieland.dto.ReviewDto;
import org.nomarch.movieland.entity.Review;
import org.nomarch.movieland.service.ReviewService;

import java.util.List;
import java.util.concurrent.Callable;

@RequiredArgsConstructor
public class GetReviewsCallable implements Callable<List<ReviewDto>> {
    private final Long movieId;
    private final ReviewService reviewService;

    @Override
    public List<ReviewDto> call() throws Exception {
        return reviewService.findByMovieId(movieId);
    }
}
