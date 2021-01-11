package org.nomarch.movieland.dao.jdbc;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.nomarch.movieland.RootApplicationContext;
import org.nomarch.movieland.TestContext;
import org.nomarch.movieland.dao.GenreDao;
import org.nomarch.movieland.entity.Genre;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DBRider
@DBUnit(caseSensitiveTableNames = false, caseInsensitiveStrategy = Orthography.LOWERCASE)
@SpringJUnitWebConfig(value = {TestContext.class, RootApplicationContext.class})
class JdbcGenreDaoITest {
    @Autowired
    private GenreDao jdbcGenreDao;

    @DisplayName("Get all genres from DB")
    @Test
    @DataSet(value = {"genres.xml", "movies.xml", "movie_to_genre.xml"})
    void testGetAll() {
        //prepare
        Genre expectedFirst = Genre.builder().id(1).name("драма").build();
        Genre expectedLast = Genre.builder().id(5).name("мелодрама").build();

        //when
        List<Genre> actualGenres = jdbcGenreDao.findAll();

        //then
        assertEquals(5, actualGenres.size());
        assertEquals(expectedFirst, actualGenres.get(0));
        assertEquals(expectedLast, actualGenres.get(4));
    }
}