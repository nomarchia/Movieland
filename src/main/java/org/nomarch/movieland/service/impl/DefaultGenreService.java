package org.nomarch.movieland.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.dao.GenreDao;
import org.nomarch.movieland.entity.Genre;

import org.nomarch.movieland.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class DefaultGenreService implements GenreService {
    @Autowired
    private GenreDao cachedGenreDao;

    @Override
    public List<Genre> findAll() {
        return cachedGenreDao.findAll();
    }
}
