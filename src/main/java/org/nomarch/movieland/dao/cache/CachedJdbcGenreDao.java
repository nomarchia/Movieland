package org.nomarch.movieland.dao.cache;

import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.dao.GenreDao;
import org.nomarch.movieland.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Repository
public class CachedJdbcGenreDao implements GenreDao {
    @Autowired
    @Qualifier("genreDao")
    GenreDao genreDao;

    List<Genre> genresCache = new CopyOnWriteArrayList<>();

    @Override
    public List<Genre> findAll() {
        log.debug("Getting all genres from the cache");
        return genresCache;
    }

    @Scheduled(fixedRateString = "${cache.renew.interval}")
    void updateCache() {
        log.debug("Update genres cache from DB");
        genresCache = genreDao.findAll();
    }
}
