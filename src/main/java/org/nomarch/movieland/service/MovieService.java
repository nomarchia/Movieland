package org.nomarch.movieland.service;

import org.nomarch.movieland.dto.movie.MovieDTO;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.dto.movie.MovieRequest;

import java.util.List;

public interface MovieService {
    List<Movie> findAll(MovieRequest movieRequest);

    List<Movie> findRandom();

    List<Movie> findByGenre(Integer genreId, MovieRequest movieRequest);

    Movie findById(Integer movieId, String currency);

    void add(MovieDTO newMovie);

    void edit(Integer movieId, MovieDTO movie);
}
