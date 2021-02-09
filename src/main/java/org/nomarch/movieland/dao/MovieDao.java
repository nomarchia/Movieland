package org.nomarch.movieland.dao;

import org.nomarch.movieland.common.SortingOrder;
import org.nomarch.movieland.entity.Movie;

import java.util.List;

public interface MovieDao {

    List<Movie> findAll(SortingOrder sortingOrder);

    List<Movie> findRandom(Integer moviesAmount);

    List<Movie> findByGenre(Integer genreId, SortingOrder sortingOrder);

    Movie findById(Long movieId);

    void add(Movie newMovie);

    void edit(Movie updatedMovie);
}
