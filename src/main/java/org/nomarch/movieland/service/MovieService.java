package org.nomarch.movieland.service;

import org.nomarch.movieland.dao.MovieDao;
import org.nomarch.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieDao movieDao;

    public List<Movie> getAll() {
        return movieDao.getAll();
    }
}
