package org.nomarch.movieland.service.impl;

import lombok.NonNull;
import org.nomarch.movieland.dao.MovieDao;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.service.MovieService;
import org.nomarch.movieland.dto.MovieRequest;
import org.nomarch.movieland.web.json.CurrencyParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DefaultMovieService implements MovieService {
    @Autowired
    private MovieDao movieDao;
    @Value("${random.movies.amount:3}")
    private Integer moviesAmount;
    @Autowired
    private CurrencyParser currencyParser;

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
    public Movie findById(Integer movieId, String currency) {
        Movie movie = movieDao.findById(movieId);
        Double currencyRate = rates.get(currency);

        movie.setPrice(movie.getPrice() / currencyRate);

        return movie;
    }

    @Scheduled(fixedRateString = "${nbu.rates.renew.interval}")
    private void getNBURates() {
        rates = currencyParser.parseCurrency();
    }
}
