package org.nomarch.movieland.entity;

import lombok.*;

@Builder
@Getter
public class Genre {
    private final Long id;
    private final String name;
}
