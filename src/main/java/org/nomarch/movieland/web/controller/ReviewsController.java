package org.nomarch.movieland.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.dto.ReviewRequest;
import org.nomarch.movieland.entity.Review;
import org.nomarch.movieland.security.impl.SecurityTokenService;
import org.nomarch.movieland.service.impl.DefaultReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/", consumes = MediaType.APPLICATION_JSON_VALUE)
public class ReviewsController {
    @Autowired
    private SecurityTokenService securityTokenService;
    @Autowired
    private DefaultReviewService defaultReviewService;

    @PostMapping(value = "review")
    public void addReview(@RequestHeader String uuid, @RequestBody ReviewRequest reviewRequest) {
        long userId = securityTokenService.findUserIdByUUIDToken(uuid);

        Review newReview = Review.builder().movieId(reviewRequest.getMovieId()).userId(userId).text(reviewRequest.getText()).build();

        defaultReviewService.save(newReview);
    }
}
