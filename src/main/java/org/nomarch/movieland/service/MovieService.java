package org.nomarch.movieland.service;

import lombok.NonNull;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.web.util.SortingUtil;

import java.util.List;

public interface MovieService {
    List<Movie> getAllMovies(@NonNull SortingUtil sortingUtil);

    List<Movie> getRandomMovies();

    List<Movie> getMoviesByGenre(@NonNull Integer genreId, @NonNull SortingUtil sortingUtil);
}
