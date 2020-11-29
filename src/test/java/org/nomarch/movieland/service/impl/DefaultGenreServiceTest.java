package org.nomarch.movieland.service.impl;

import org.junit.jupiter.api.Test;
import org.nomarch.movieland.TestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitWebConfig(TestContext.class)
class DefaultGenreServiceTest {
    @Autowired
    private DefaultGenreService defaultGenreService;
    @Autowired
    private JdbcTemplate jdbcTemplate;



    @Test
    void testUpdateGenresCache() throws InterruptedException {
        defaultGenreService = new DefaultGenreService();

        assertEquals(15, defaultGenreService.getAllGenres().size());

        jdbcTemplate.update("INSERT INTO public.genres(name) VALUES('сериал')");

        Thread.sleep(2000);
        assertEquals(15, defaultGenreService.getAllGenres().size());



        assertEquals(16, defaultGenreService.getAllGenres().size());
    }


}