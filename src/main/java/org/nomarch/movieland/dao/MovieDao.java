package org.nomarch.movieland.dao;

import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.dto.MovieRequest;

import java.util.List;

public interface MovieDao {
    List<Movie> findAll(MovieRequest movieRequest);
    List<Movie> findRandom(Integer moviesAmount);
    List<Movie> findByGenre(Integer genreId, MovieRequest movieRequest);
    Movie findById(Integer movieId);
}
