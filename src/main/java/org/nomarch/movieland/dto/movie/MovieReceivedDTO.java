package org.nomarch.movieland.dto.movie;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MovieReceivedDTO {
    private String nameRussian;
    private String nameNative;
    private Integer yearOfRelease;
    private String description;
    private Double price;
    private String picturePath;
    private int[] countries;
    private int[] genres;
}

