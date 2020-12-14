package org.nomarch.movieland.service.impl;

import lombok.NonNull;
import org.nomarch.movieland.dao.MovieDao;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.service.MovieService;
import org.nomarch.movieland.entity.MovieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultMovieService implements MovieService {
    @Autowired
    private MovieDao movieDao;
    @Value("${random.movies.amount:3}")
    private Integer moviesAmount;

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
}
