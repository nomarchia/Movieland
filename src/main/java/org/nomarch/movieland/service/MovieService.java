package org.nomarch.movieland.service;

import org.nomarch.movieland.common.CurrencyCode;
import org.nomarch.movieland.dto.FullMovieDto;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.request.GetMovieRequest;

import java.util.List;

public interface MovieService {
    List<Movie> findAll(GetMovieRequest getMovieRequest);

    List<Movie> findRandom();

    List<Movie> findByGenre(Integer genreId, GetMovieRequest getMovieRequest);

    FullMovieDto findById(Long movieId, CurrencyCode currencyCode);

    void add(Movie newMovie);

    void edit(Movie updatedMovie);
}
