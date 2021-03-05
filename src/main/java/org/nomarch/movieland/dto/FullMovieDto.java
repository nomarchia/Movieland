package org.nomarch.movieland.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class FullMovieDto {
    private Long id;
    private String nameRussian;
    private String nameNative;
    private Integer yearOfRelease;
    private String description;
    private Double price;
    private String picturePath;
    private List<ReturnedEntity> countryList;
    private List<ReturnedEntity> genreList;
    private List<ReturnedEntity> reviewList;
}
