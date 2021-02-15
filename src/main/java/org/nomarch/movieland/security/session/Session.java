package org.nomarch.movieland.security.session;

import lombok.Builder;
import lombok.Getter;
import org.nomarch.movieland.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class Session {
    private final User user;
    private final UUID uuidToken;
    private final LocalDateTime expiryTime;
}
