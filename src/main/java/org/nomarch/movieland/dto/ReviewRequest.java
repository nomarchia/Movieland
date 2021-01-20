package org.nomarch.movieland.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewRequest {
    private int movieId;
    private String text;
}
