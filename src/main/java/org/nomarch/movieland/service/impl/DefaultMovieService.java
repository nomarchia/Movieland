package org.nomarch.movieland.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.nomarch.movieland.common.currency.Currency;
import org.nomarch.movieland.dao.MovieDao;
import org.nomarch.movieland.dto.movie.MovieReceivedDTO;
import org.nomarch.movieland.dto.movie.MovieReturnedDTO;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.service.*;
import org.nomarch.movieland.dto.movie.MovieRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DefaultMovieService implements MovieService {
    private final MovieDao movieDao;
    private final CurrencyService currencyService;
    private final GenreService genreService;
    private final CountryService countryService;
    private final ReviewService reviewService;
    private final ModelMapper modelMapper;
    @Value("${random.movies.amount:3}")
    private Integer moviesAmount;

    private Map<String, Double> rates;

    @Override
    public List<Movie> findAll(@NonNull MovieRequest movieRequest) {
        return movieDao.findAll(movieRequest);
    }

    @Override
    public List<Movie> findRandom(){
        return movieDao.findRandom(moviesAmount);
    }

    @Override
    public List<Movie> findByGenre(@NonNull Integer genreId, @NonNull MovieRequest movieRequest) {
        return movieDao.findByGenre(genreId, movieRequest);
    }

    @Override
    public MovieReturnedDTO findById(Long movieId, Currency currency) {
        Movie movie = movieDao.findById(movieId);
        Double currencyRate = currencyService.getCurrencyRate(currency);

        movie.setPrice(movie.getPrice() / currencyRate);

        MovieReturnedDTO movieDTO = modelMapper.map(movie, MovieReturnedDTO.class);

        movieDTO.setGenreList(genreService.findByMovieId(movieId));
        movieDTO.setCountryList(countryService.findByMovieId(movieId));
        movieDTO.setReviewList(reviewService.findByMovieId(movieId));

        return movieDTO;
    }

    @Override
    public void add(MovieReceivedDTO newMovie) {
        Movie movie = modelMapper.map(newMovie, Movie.class);
        movieDao.add(movie);
    }

    @Override
    public void edit(Long movieId, MovieReceivedDTO editedMovie) {
        Movie movie = modelMapper.map(editedMovie, Movie.class);
        movie.setId(movieId);
        movieDao.edit(movie);
    }
}
