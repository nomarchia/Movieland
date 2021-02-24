package org.nomarch.movieland.entity;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    private Long id;
    private String name;
}
