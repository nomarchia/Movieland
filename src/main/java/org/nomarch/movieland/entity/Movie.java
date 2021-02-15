package org.nomarch.movieland.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    private Long id;
    private String nameNative;
    private String nameRussian;
    private Integer yearOfRelease;
    private String description;
    private Double rating;
    private Double price;
    private String picturePath;
    private List<Genre> genres;
    private List<Country> countries;
    private List<Review> reviews;
}
