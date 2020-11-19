package org.nomarch.movieland.service;

import org.nomarch.movieland.entity.Movie;

import java.util.List;

public interface MovieService {
    public List<Movie> getAllMovies();

    public List<Movie> getRandomMovies();

    public List<Movie> getMoviesByGenre(Integer genreId);
}
