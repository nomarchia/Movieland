package org.nomarch.movieland.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginInfo {
    private final String uuid;
    private final String nickname;
}
