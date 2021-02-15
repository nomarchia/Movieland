package org.nomarch.movieland.dao.cache;

import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.dao.GenreDao;
import org.nomarch.movieland.entity.Genre;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Primary
public class CachedGenreDao implements GenreDao {
    private final GenreDao genreDao;
    private volatile List<Genre> genresCache;

    public CachedGenreDao(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public List<Genre> findAll() {
        if (genresCache == null) {
            updateCache();
        }
        log.debug("Returning all genres from the cache");
        return new ArrayList<>(genresCache);
    }

    @Override
    public List<Genre> findByMovieId(Long movieId) {
        return genreDao.findByMovieId(movieId);
    }

    @Scheduled(fixedRateString = "${movies.cache.renew.interval}")
    @PostConstruct
    private void updateCache() {
        log.debug("Update genres cache from DB");
        genresCache = genreDao.findAll();
    }
}
