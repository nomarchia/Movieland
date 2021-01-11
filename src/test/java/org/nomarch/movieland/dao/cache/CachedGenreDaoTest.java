package org.nomarch.movieland.dao.cache;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.nomarch.movieland.RootApplicationContext;
import org.nomarch.movieland.TestContext;
import org.nomarch.movieland.dao.GenreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@DBRider
@DBUnit(caseSensitiveTableNames = false, caseInsensitiveStrategy = Orthography.LOWERCASE)
@SpringJUnitWebConfig(value = {TestContext.class, RootApplicationContext.class})
@DataSet(value = {"movies.xml", "genres.xml", "movie_to_genre.xml"})
class CachedGenreDaoTest {
    @Autowired
    private GenreDao cachedGenreDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // TODO: Test work well on my machine and checks that cache works.
    // I remember you said something about that test, but didn't said what's wrong exactly.
    // I suppose that's something is bad with injecting jdbcTemplate bean and using it/
    // If this test is bad, please explain how to implement such test in a better way.
    @DisplayName("Test genres cache auto-update after interval")
    @Test
    void testUpdateGenresCache() throws InterruptedException {
        log.info("Sleep thread for 15 seconds to wait cache to be updated from DB");
        Thread.sleep(15000);
        assertEquals(5, cachedGenreDao.findAll().size());

        log.info("Update genres table, add new genre");
        jdbcTemplate.update("INSERT INTO public.genres(id, name) VALUES(6, 'сериал')");

        log.info("Check that cache was not updated immediately");
        assertEquals(5, cachedGenreDao.findAll().size());
        log.info("Sleep thread for 15 seconds");
        Thread.sleep(15000);

        log.info("Check updated cache after waiting for scheduled interval");
        assertEquals(6, cachedGenreDao.findAll().size());
    }
}