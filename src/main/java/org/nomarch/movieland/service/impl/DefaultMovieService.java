package org.nomarch.movieland.service.impl;

import org.nomarch.movieland.dao.MovieDao;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultMovieService implements MovieService {
    @Autowired
    private MovieDao movieDao;

    @Override
    public List<Movie> getAllMovies() {
        return movieDao.getAllMovies();
    }

    @Override
    public List<Movie> getRandomMovies(){
        return movieDao.getRandomMovies();
    }

    @Override
    public List<Movie> getMoviesByGenre(Integer genreId) {
        return movieDao.getMoviesByGenre(genreId);
    }
}
