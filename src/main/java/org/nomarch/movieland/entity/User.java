package org.nomarch.movieland.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class User {
    @Setter
    private Long id;
    private String nickname;
    private String email;
    private String password;
    private UserRole role;
}
