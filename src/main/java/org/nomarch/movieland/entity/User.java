package org.nomarch.movieland.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class User {
    private long id;
    private String fullName;
    private String email;
    private String password;
}
