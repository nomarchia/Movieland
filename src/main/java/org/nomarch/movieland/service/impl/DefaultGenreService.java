package org.nomarch.movieland.service.impl;

import org.nomarch.movieland.dao.GenreDao;
import org.nomarch.movieland.entity.Genre;

import org.nomarch.movieland.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DefaultGenreService implements GenreService {
    @Autowired
    private GenreDao genreDao;
    private List<Genre> genresCache;

//    public DefaultGenreService() {
//        updateGenresCache();
//    }

    @Override
    public List<Genre> getAllGenres() {
        if (Objects.isNull(genresCache) || genresCache.isEmpty()) {
            updateGenresCache();
        }
        return genresCache;
    }

    @Override
    @Scheduled(fixedRateString = "${cache.renew.interval}", fixedDelayString = "${cache.init.delay}")
    public void updateGenresCache() {
        if (Objects.nonNull(genresCache)) {
            genresCache.clear();
        }
        genresCache = genreDao.getAllGenres();
    }
}
