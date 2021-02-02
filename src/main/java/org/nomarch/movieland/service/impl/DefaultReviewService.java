package org.nomarch.movieland.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.nomarch.movieland.dao.ReviewDao;
import org.nomarch.movieland.dto.review.ReviewReturnedDTO;
import org.nomarch.movieland.dto.user.UserReturnedDTO;
import org.nomarch.movieland.entity.Review;
import org.nomarch.movieland.entity.User;
import org.nomarch.movieland.service.ReviewService;
import org.nomarch.movieland.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultReviewService implements ReviewService {
    private final ReviewDao reviewDao;
    private final UserService userService;
    private final ModelMapper mapper;

    @Override
    public void save(Review review) {
        reviewDao.save(review);
    }

    @Override
    public List<ReviewReturnedDTO> findByMovieId(Long movieId) {
        List<Review> reviews = reviewDao.findByMovieId(movieId);
        List<ReviewReturnedDTO> reviewDTOS = new ArrayList<>();

        for (Review review : reviews) {
            ReviewReturnedDTO reviewReturnedDTO = mapper.map(review, ReviewReturnedDTO.class);

            User user = userService.findUserByReviewId(review.getId());
            reviewReturnedDTO.setUser(mapper.map(user, UserReturnedDTO.class));

            reviewDTOS.add(reviewReturnedDTO);
        }

        return reviewDTOS;
    }
}
