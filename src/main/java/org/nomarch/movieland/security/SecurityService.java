package org.nomarch.movieland.security;

import org.nomarch.movieland.dto.UserUUID;
import org.springframework.lang.NonNull;

public interface SecurityService {
    UserUUID login(String email, String password);

    void logout(String uuidToken);

    long findUserIdByUUIDToken(String uuidToken);
}
