package org.nomarch.movieland.service;

import lombok.NonNull;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.entity.MovieRequest;

import java.util.List;

public interface MovieService {
    List<Movie> findAll(MovieRequest movieRequest);

    List<Movie> findRandom();

    List<Movie> findByGenre(Integer genreId, MovieRequest movieRequest);
}
