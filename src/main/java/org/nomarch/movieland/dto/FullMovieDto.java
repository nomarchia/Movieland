package org.nomarch.movieland.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nomarch.movieland.entity.Country;
import org.nomarch.movieland.entity.Genre;

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
    private List<Country> countryList;
    private List<Genre> genreList;
    private List<ReviewDto> reviewList;
}
