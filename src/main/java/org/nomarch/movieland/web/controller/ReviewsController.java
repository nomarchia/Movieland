package org.nomarch.movieland.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.mapper.ReviewDtoMapper;
import org.nomarch.movieland.request.ReviewRequest;
import org.nomarch.movieland.entity.Review;
import org.nomarch.movieland.entity.User;
import org.nomarch.movieland.security.UserHolder;
import org.nomarch.movieland.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ReviewsController {
    private final ReviewService reviewService;
    private final ReviewDtoMapper mapper;

    @PostMapping(value = "review")
    @ResponseStatus(HttpStatus.CREATED)
    public void addReview(@RequestBody ReviewRequest reviewRequest) {
        log.debug("POST request by url /review to add new review");

        Review newReview = mapper.dtoToReview(reviewRequest);

        User user = UserHolder.getUser();
        newReview.setUser(user);

        log.debug("Saving new review: {}, added by user: {}", newReview, user);
        reviewService.save(newReview);
    }
}
