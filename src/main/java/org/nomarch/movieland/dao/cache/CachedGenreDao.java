package org.nomarch.movieland.dao.cache;

import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.dao.GenreDao;
import org.nomarch.movieland.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Repository
@Component("cachedGenreDao")
public class CachedGenreDao implements GenreDao {
    @Autowired
    private GenreDao jdbcGenreDao;
    private volatile List<Genre> genresCache = new CopyOnWriteArrayList<>();

    @Override
    public List<Genre> findAll() {
        log.debug("Returning all genres from the cache");
        return genresCache;
    }

    @Scheduled(fixedRateString = "${movies.cache.renew.interval}")
    private void updateCache() {
        if (genresCache.size() > 0) {
            genresCache.clear();
        }
        log.debug("Update genres cache from DB");
        genresCache.addAll(jdbcGenreDao.findAll());
    }
}
