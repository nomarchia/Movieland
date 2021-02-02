package org.nomarch.movieland.entity;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    private Long id;
    private String nameNative;
    private String nameRussian;
    private Integer yearOfRelease;
    private String description;
    private Double rating;
    private Double price;
    private String picturePath;
    private int[] genres;
    private int[] countries;
    private int[] reviews;
}
