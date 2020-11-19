package org.nomarch.movieland.service.impl;

import org.nomarch.movieland.dao.GenreDao;
import org.nomarch.movieland.entity.Genre;
import org.nomarch.movieland.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultGenreService implements GenreService {
    @Autowired
    private GenreDao genreDao;

    @Override
    public List<Genre> getAllGenres() {
        return genreDao.getAllGenres();
    }
}
