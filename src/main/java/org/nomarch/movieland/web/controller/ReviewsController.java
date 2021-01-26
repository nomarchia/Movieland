package org.nomarch.movieland.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.dto.ReviewRequest;
import org.nomarch.movieland.entity.Review;
import org.nomarch.movieland.entity.User;
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
        log.debug("POST request by url \"/api/v1/review\" for user with token: {}", uuid);
        User user = securityTokenService.findUserByUUIDToken(uuid);

        Review newReview = Review.builder().movieId(reviewRequest.getMovieId()).userId(user.getId()).text(reviewRequest.getText()).build();

        log.debug("Saving new review: {}", newReview);
        defaultReviewService.save(newReview);
    }
}
