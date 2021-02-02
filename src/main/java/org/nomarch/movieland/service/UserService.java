package org.nomarch.movieland.service;

import org.nomarch.movieland.entity.User;

public interface UserService {
    User findUserByReviewId(Long reviewId);
}
