package org.nomarch.movieland.dao;

import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.web.util.SortingUtil;

import java.util.List;

public interface MovieDao {
    List<Movie> getAllMovies(SortingUtil sortingUtil);
    List<Movie> getRandomMovies();
    List<Movie> getMoviesByGenre(Integer genreId, SortingUtil sortingUtil);
}
