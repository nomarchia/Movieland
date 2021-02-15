package org.nomarch.movieland.service;

import org.nomarch.movieland.common.Currency;
import org.nomarch.movieland.dto.FullMovieDto;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.request.GetMovieRequest;
import org.nomarch.movieland.request.SaveMovieRequest;

import java.util.List;

public interface MovieService {
    List<Movie> findAll(GetMovieRequest getMovieRequest);

    List<Movie> findRandom();

    List<Movie> findByGenre(Integer genreId, GetMovieRequest getMovieRequest);

    FullMovieDto findById(Long movieId, Currency currency);

    void add(SaveMovieRequest newMovie);

    void edit(Long movieId, SaveMovieRequest movie);
}
