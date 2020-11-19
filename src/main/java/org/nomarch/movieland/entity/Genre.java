package org.nomarch.movieland.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Genre {
    private int id;
    private String name;
}
