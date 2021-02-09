package org.nomarch.movieland.security;

import org.nomarch.movieland.dto.LoginInfo;
import org.nomarch.movieland.entity.User;

public interface SecurityService {
    LoginInfo login(String email, String password);

    void logout(String uuidToken);

    User findUserByUUIDToken(String uuidToken);
}
