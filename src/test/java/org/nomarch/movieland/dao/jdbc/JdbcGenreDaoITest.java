package org.nomarch.movieland.dao.jdbc;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.nomarch.movieland.MovielandApplicationContext;
import org.nomarch.movieland.TestContext;
import org.nomarch.movieland.dao.GenreDao;
import org.nomarch.movieland.entity.Genre;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@SpringJUnitWebConfig(value = {TestContext.class, MovielandApplicationContext.class})
class JdbcGenreDaoITest {
    @Autowired
    private GenreDao jdbcGenreDao;

    @DisplayName("Get all genres from DB")
    @Test
    void testGetAll() {
        //prepare
        Genre expectedFirst = Genre.builder().id(1L).name("драма").build();
        Genre expectedFifth = Genre.builder().id(5L).name("мелодрама").build();

        //when
        List<Genre> actualGenres = jdbcGenreDao.findAll();

        //then
        assertEquals(5, actualGenres.size());
        assertTrue(new ReflectionEquals(expectedFirst).matches(actualGenres.get(0)));
        assertTrue(new ReflectionEquals(expectedFifth).matches(actualGenres.get(4)));
    }

    @DisplayName("Get all genres by movie id")
    @Test
    @DataSet(value = "movies_genres_countries_and_many_to_many_tables.xml", skipCleaningFor = {"genres"})
    void testGetGenresByMovieId() {
        //prepare
        Genre expected = Genre.builder().id(5L).name("мелодрама").build();

        //when
        List<Genre> actualGenres = jdbcGenreDao.findByMovieId(5L);

        //then
        assertNotNull(actualGenres);
        assertEquals(1, actualGenres.size());
        assertTrue(new ReflectionEquals(expected).matches(actualGenres.get(0)));
    }
}