package org.nomarch.movieland.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
public class Genre {
    private int id;
    private String name;
}
