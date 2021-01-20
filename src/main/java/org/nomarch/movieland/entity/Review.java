package org.nomarch.movieland.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Review {
    private int movieId;
    private long userId;
    private String text;
}
