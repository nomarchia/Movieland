package org.nomarch.movieland.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.nomarch.movieland.dto.review.ReviewReceivedDTO;
import org.nomarch.movieland.entity.Review;
import org.nomarch.movieland.entity.User;
import org.nomarch.movieland.security.SecurityService;
import org.nomarch.movieland.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ReviewsController {
    private final SecurityService securityService;
    private final ReviewService reviewService;
    private final ModelMapper mapper;

    @PostMapping(value = "review")
    @ResponseStatus(HttpStatus.OK)
    public void addReview(@RequestHeader String uuid, @RequestBody ReviewReceivedDTO reviewDTO) {
        log.debug("POST request by url \"/api/v1/review\" for user with token: {}", uuid);
        User user = securityService.findUserByUUIDToken(uuid);

        Review newReview = mapper.map(reviewDTO, Review.class);
        newReview.setUser(user);

        log.debug("Saving new review: {}", newReview);
        reviewService.save(newReview);
    }
}
