package org.nomarch.movieland.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserUUID {
    private String uuid;
    private String nickname;
}
