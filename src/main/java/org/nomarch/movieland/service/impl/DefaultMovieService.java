package org.nomarch.movieland.service.impl;

import lombok.RequiredArgsConstructor;
import org.nomarch.movieland.common.Currency;
import org.nomarch.movieland.dao.MovieDao;
import org.nomarch.movieland.dto.FullMovieDto;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.mapper.MovieDtoMapper;
import org.nomarch.movieland.request.GetMovieRequest;
import org.nomarch.movieland.request.SaveMovieRequest;
import org.nomarch.movieland.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultMovieService implements MovieService {

    private final MovieDao movieDao;
    private final CurrencyService currencyService;
    private final GenreService genreService;
    private final CountryService countryService;
    private final ReviewService reviewService;
    private final MovieDtoMapper movieDtoMapper;
    @Value("${random.movies.amount:3}")
    private Integer moviesAmount;

    @Override
    public List<Movie> findAll(GetMovieRequest getMovieRequest) {
        return movieDao.findAll(getMovieRequest);
    }

    @Override
    public List<Movie> findRandom(){
        return movieDao.findRandom(moviesAmount);
    }

    @Override
    public List<Movie> findByGenre(Integer genreId, GetMovieRequest getMovieRequest) {
        return movieDao.findByGenre(genreId, getMovieRequest);
    }

    @Override
    public FullMovieDto findById(Long movieId, Currency currency) {
        Movie movie = movieDao.findById(movieId);

        Double currencyRate = currencyService.getCurrencyRate(currency);
        movie.setPrice(movie.getPrice() / currencyRate);

        FullMovieDto movieDTO = movieDtoMapper.movieToDto(movie);

        movieDTO.setGenreList(genreService.findByMovieId(movieId));
        movieDTO.setCountryList(countryService.findByMovieId(movieId));
        movieDTO.setReviewList(reviewService.findByMovieId(movieId));

        return movieDTO;
    }

    @Override
    public void add(SaveMovieRequest newMovie) {
        Movie movie = movieDtoMapper.dtoToMovie(newMovie);
        movieDao.add(movie);
    }

    @Override
    public void edit(Long movieId, SaveMovieRequest editedMovie) {
        Movie movie = movieDtoMapper.dtoToMovie(editedMovie);
        movie.setId(movieId);
        movieDao.edit(movie);
    }
}
