package org.nomarch.movieland.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewDto {
    private Long id;
    private UserDto user;
    private String text;
}
