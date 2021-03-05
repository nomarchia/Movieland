package org.nomarch.movieland.dao;

import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.request.GetMovieRequest;

import java.util.List;

public interface MovieDao {

    List<Movie> findAll(GetMovieRequest getMovieRequest);

    List<Movie> findRandom(Integer moviesAmount);

    List<Movie> findByGenre(Integer genreId, GetMovieRequest getMovieRequest);

    Movie findById(Long movieId);

    void add(Movie newMovie);

    Long addAndReturnId(Movie newMovie);

    void edit(Movie updatedMovie);
}
