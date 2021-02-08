package org.nomarch.movieland.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SaveMovieRequest {
    private String nameRussian;
    private String nameNative;
    private Integer yearOfRelease;
    private String description;
    private Double price;
    private String picturePath;
    //should be list of entities
    private int[] countries;
    private int[] genres;
}

