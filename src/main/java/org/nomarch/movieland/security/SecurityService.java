package org.nomarch.movieland.security;

import org.nomarch.movieland.dto.user.UserUUID;
import org.nomarch.movieland.entity.User;

public interface SecurityService {
    UserUUID login(String email, String password);

    void logout(String uuidToken);

    User findUserByUUIDToken(String uuidToken);
}
