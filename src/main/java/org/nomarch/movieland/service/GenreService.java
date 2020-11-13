package org.nomarch.movieland.service;

import org.nomarch.movieland.dao.GenreDao;
import org.nomarch.movieland.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    @Autowired
    private GenreDao genreDao;

    public List<Genre> getAll() {
        return genreDao.getAll();
    }
}
