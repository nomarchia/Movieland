package org.nomarch.movieland.service.impl;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.nomarch.movieland.MainApplicationContext;
import org.nomarch.movieland.TestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@DBRider
@DBUnit(caseSensitiveTableNames = false, caseInsensitiveStrategy = Orthography.LOWERCASE)
@SpringJUnitWebConfig(value = {TestContext.class, MainApplicationContext.class})
@DataSet("genres.xml")
class DefaultGenreServiceTest {
    @Autowired
    private DefaultGenreService defaultGenreService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("Test check genres cache update")
    @Test
    void testUpdateGenresCache() throws InterruptedException {
        assertEquals(5, defaultGenreService.getAllGenres().size());

        log.debug("Update genres table, add new genre");
        jdbcTemplate.update("INSERT INTO public.genres(id, name) VALUES(6, 'сериал')");

        log.debug("Check that cache was not updated immediately");
        assertEquals(5, defaultGenreService.getAllGenres().size());
        log.debug("Sleep thread for 20 seconds");
        Thread.sleep(20000);

        log.debug("Checking updated cache after waiting for scheduled interval");
        assertEquals(6, defaultGenreService.getAllGenres().size());
    }


}