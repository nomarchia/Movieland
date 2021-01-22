package org.nomarch.movieland.dao;

import org.nomarch.movieland.dto.movie.MovieDTO;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.dto.movie.MovieRequest;

import java.util.List;

public interface MovieDao {
    List<Movie> findAll(MovieRequest movieRequest);
    List<Movie> findRandom(Integer moviesAmount);
    List<Movie> findByGenre(Integer genreId, MovieRequest movieRequest);
    Movie findById(Integer movieId);
    void add(MovieDTO newMovie);
    void edit(Integer movieId, MovieDTO updatedMovie);
}
