package org.nomarch.movieland.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class User {
    @Setter
    private Long id;
    private final String nickname;
    private final String email;
    private final String password;
    private final UserRole role;
}
