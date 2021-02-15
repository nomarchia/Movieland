package org.nomarch.movieland.dao.cache;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.junit5.api.DBRider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.nomarch.movieland.MovielandApplicationContext;
import org.nomarch.movieland.TestContext;
import org.nomarch.movieland.dao.GenreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@SpringJUnitWebConfig(value = {TestContext.class, MovielandApplicationContext.class})
class CachedGenreDaoTest {
    @Autowired
    private GenreDao cachedGenreDao;
    @Autowired
    private NamedParameterJdbcTemplate namedParamJdbcTemplate;

    @DisplayName("Test genres cache auto-update after interval")
    @Test
    void testUpdateGenresCache() throws InterruptedException {
        log.info("Check that cache has been updated on start");
        assertEquals(5, cachedGenreDao.findAll().size());

        log.info("Update genres table, add new genre");
        namedParamJdbcTemplate.update("INSERT INTO public.genres(name) VALUES(:genre_name)", new MapSqlParameterSource("genre_name", "сериал"));

        log.info("Check that cache was not updated immediately");
        assertEquals(5, cachedGenreDao.findAll().size());
        log.info("Sleep thread for 15 seconds");
        Thread.sleep(15000);

        log.info("Check updated cache after waiting for scheduled interval");
        assertEquals(6, cachedGenreDao.findAll().size());

        //after
        cleanUp();
    }

    private void cleanUp() {
        namedParamJdbcTemplate.update("DELETE FROM public.genres WHERE name = :genre_name", new MapSqlParameterSource("genre_name", "сериал"));
    }
}