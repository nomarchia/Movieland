package org.nomarch.movieland.dao.jdbc;

import org.nomarch.movieland.MainApplicationContext;
import org.nomarch.movieland.dao.GenreDao;
import org.nomarch.movieland.entity.Genre;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitWebConfig(MainApplicationContext.class)
class JdbcGenreDaoITest {
    @Autowired
    private GenreDao genreDao;

    @DisplayName("Get all genres from DB")
    @Test
    void testGetAll() {
        //prepare
        Genre expectedFirst = Genre.builder().id(1).name("драма").build();
        Genre expectedLast = Genre.builder().id(15).name("вестерн").build();

        //when
        List<Genre> actualGenres = genreDao.getAll();

        //then
        assertEquals(15, actualGenres.size());
        assertEquals(expectedFirst, actualGenres.get(0));
        assertEquals(expectedLast, actualGenres.get(14));
    }

    class SortGenreById implements Comparator<Genre>
    {
        public int compare(Genre a, Genre b)
        {
            return a.getId() - b.getId();
        }
    }
}