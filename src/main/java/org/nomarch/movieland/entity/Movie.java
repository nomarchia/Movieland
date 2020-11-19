package org.nomarch.movieland.entity;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Movie {
    private int id;
    private String nameNative;
    private String nameRussian;
    private String country;
    private int year;
    private String description;
    private Double rating;
    private Double price;
    private String posterImg;
}
