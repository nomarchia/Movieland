package org.nomarch.movieland.service.impl;

import lombok.NonNull;
import org.nomarch.movieland.dao.MovieDao;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.service.MovieService;
import org.nomarch.movieland.web.util.SortingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultMovieService implements MovieService {
    @Autowired
    private MovieDao movieDao;

    @Override
    public List<Movie> getAllMovies(@NonNull SortingUtil sortingUtil) {
        return movieDao.getAllMovies(sortingUtil);
    }

    @Override
    public List<Movie> getRandomMovies(){
        return movieDao.getRandomMovies();
    }

    @Override
    public List<Movie> getMoviesByGenre(@NonNull Integer genreId, @NonNull SortingUtil sortingUtil) {
        return movieDao.getMoviesByGenre(genreId, sortingUtil);
    }
}
