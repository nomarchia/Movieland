package org.nomarch.movieland.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Country {
    private int id;
    private String name;
}
