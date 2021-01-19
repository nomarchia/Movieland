package org.nomarch.movieland.security;

import org.nomarch.movieland.dto.UserUUID;

public interface SecurityService {
    UserUUID login(String email, String password);

    void logout(String uuidToken);
}
