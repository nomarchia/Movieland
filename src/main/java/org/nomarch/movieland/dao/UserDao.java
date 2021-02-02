package org.nomarch.movieland.dao;

import org.nomarch.movieland.entity.User;

public interface UserDao {
    User login(String email, String password);

    User findUserByReviewId(Long reviewId);
}
