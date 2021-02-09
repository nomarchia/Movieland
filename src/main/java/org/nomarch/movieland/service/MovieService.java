package org.nomarch.movieland.service;

import org.nomarch.movieland.common.Currency;
import org.nomarch.movieland.common.SortingOrder;
import org.nomarch.movieland.request.SaveMovieRequest;
import org.nomarch.movieland.dto.FullMovieDto;
import org.nomarch.movieland.entity.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> findAll(SortingOrder sortingOrder);

    List<Movie> findRandom();

    List<Movie> findByGenre(Integer genreId, SortingOrder sortingOrder);

    FullMovieDto findById(Long movieId, Currency currency);

    void add(SaveMovieRequest newMovie);

    void edit(Long movieId, SaveMovieRequest movie);
}
