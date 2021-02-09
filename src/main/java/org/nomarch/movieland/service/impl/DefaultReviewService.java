package org.nomarch.movieland.service.impl;

import lombok.RequiredArgsConstructor;
import org.nomarch.movieland.dao.ReviewDao;
import org.nomarch.movieland.dto.ReviewDto;
import org.nomarch.movieland.entity.Review;
import org.nomarch.movieland.entity.User;
import org.nomarch.movieland.mapper.ReviewDtoMapper;
import org.nomarch.movieland.service.ReviewService;
import org.nomarch.movieland.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultReviewService implements ReviewService {
    private final ReviewDao reviewDao;
    private final UserService userService;
    private final ReviewDtoMapper reviewDtoMapper;

    @Override
    public void save(Review review) {
        reviewDao.save(review);
    }

    @Override
    public List<ReviewDto> findByMovieId(Long movieId) {
        List<Review> reviews = reviewDao.findByMovieId(movieId);
        List<ReviewDto> reviewDTOS = new ArrayList<>();

        for (Review review : reviews) {
            User user = userService.findUserByReviewId(review.getId());
            review.setUser(user);
            ReviewDto reviewDto = reviewDtoMapper.reviewToDto(review);

            reviewDTOS.add(reviewDto);
        }

        return reviewDTOS;
    }
}
