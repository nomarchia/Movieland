package org.nomarch.movieland.dto.movie;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class MovieDTO {
    private String nameRussian;
    private String nameNative;
    private Integer yearOfRelease;
    private String description;
    private Double price;
    private String picturePath;
    private int[] countries;
    private int[] genres;
}

