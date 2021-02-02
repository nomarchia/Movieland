package org.nomarch.movieland.service;

import org.nomarch.movieland.common.currency.Currency;
import org.nomarch.movieland.dto.movie.MovieReceivedDTO;
import org.nomarch.movieland.dto.movie.MovieReturnedDTO;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.dto.movie.MovieRequest;

import java.util.List;

public interface MovieService {
    List<Movie> findAll(MovieRequest movieRequest);

    List<Movie> findRandom();

    List<Movie> findByGenre(Integer genreId, MovieRequest movieRequest);

    MovieReturnedDTO findById(Long movieId, Currency currency);

    void add(MovieReceivedDTO newMovie);

    void edit(Long movieId, MovieReceivedDTO movie);
}
