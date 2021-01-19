package org.nomarch.movieland.security.session;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class Session {
    private String nickName;
    private UUID uuidToken;
    private LocalDateTime expiryTime;
}
