package org.nomarch.movieland.entity;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private Long id;
    private Long movieId;
    private User user;
    private String text;
}


