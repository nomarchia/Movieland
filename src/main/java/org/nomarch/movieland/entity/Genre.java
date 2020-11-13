package org.nomarch.movieland.entity;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Genre {
    private int id;
    private String name;
}
