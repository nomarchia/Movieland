package org.nomarch.movieland.service.impl;

import lombok.RequiredArgsConstructor;
import org.nomarch.movieland.dao.UserDao;
import org.nomarch.movieland.entity.User;
import org.nomarch.movieland.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {
    private final UserDao userDao;

    @Override
    public User findUserByReviewId(Long reviewId) {
        return userDao.findUserByReviewId(reviewId);
    }
}
