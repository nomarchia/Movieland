package org.nomarch.movieland.dto.review;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewReceivedDTO {
    private Long movieId;
    private String text;
}
