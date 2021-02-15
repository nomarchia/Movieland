package org.nomarch.movieland.request;

import lombok.Getter;
import lombok.Setter;
import org.nomarch.movieland.entity.Country;
import org.nomarch.movieland.entity.Genre;

import java.util.List;

@Setter
@Getter
public class SaveMovieRequest {
    private String nameRussian;
    private String nameNative;
    private Integer yearOfRelease;
    private String description;
    private Double price;
    private String picturePath;
    private List<Country> countries;
    private List<Genre> genres;
}

