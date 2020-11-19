package org.nomarch.movieland.dao;

import org.nomarch.movieland.entity.Movie;

import java.util.List;

public interface MovieDao {
    List<Movie> getAllMovies();
    List<Movie> getRandomMovies();
    List<Movie> getMoviesByGenre(Integer genreId);
}
