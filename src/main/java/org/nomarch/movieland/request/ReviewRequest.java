package org.nomarch.movieland.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewRequest {
    private Long movieId;
    private String text;
}
