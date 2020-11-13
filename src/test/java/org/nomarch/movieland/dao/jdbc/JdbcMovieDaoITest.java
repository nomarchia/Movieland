package org.nomarch.movieland.dao.jdbc;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.nomarch.movieland.MainApplicationContext;
import org.nomarch.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitWebConfig (MainApplicationContext.class)
class JdbcMovieDaoITest {
    @Autowired
    private JdbcMovieDao jdbcMovieDao;

    @DisplayName("Get all movies from DB")
    @Test
    void testGetAll() {
        //prepare
        Movie expectedMovieFirst = Movie.builder()
                .id(1)
                .name("Побег из Шоушенка/The Shawshank Redemption")
                .country("США")
                .year(1994)
                .description("Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решетки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, вооруженный живым умом и доброй душой, отказывается мириться с приговором судьбы и начинает разрабатывать невероятно дерзкий план своего освобождения.")
                .rating(8.9)
                .price(123.45)
                .posterImg("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg")
                .build();
        Movie expectedMovieLast = Movie.builder()
                .id(25)
                .name("Танцующий с волками/Dances with Wolves")
                .country("США, Великобритания")
                .year(1990)
                .description("Действие фильма происходит в США во времена Гражданской войны. Лейтенант американской армии Джон Данбар после ранения в бою просит перевести его на новое место службы ближе к западной границе США. Место его службы отдалённый маленький форт. Непосредственный его командир покончил жизнь самоубийством, а попутчик Данбара погиб в стычке с индейцами после того, как довез героя до места назначения. Людей, знающих, что Данбар остался один в форте и должен выжить в условиях суровой природы, и в соседстве с кажущимися негостеприимными коренными обитателями Северной Америки, просто не осталось. Казалось, он покинут всеми. Постепенно лейтенант осваивается, он ведет записи в дневнике…")
                .rating(8.00)
                .price(120.55)
                .posterImg("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg")
                .build();
        //when
        List<Movie> actualMovies = jdbcMovieDao.getAll();
        Collections.sort(actualMovies, new SortById());
        //then
        assertEquals(25, actualMovies.size());
        Assert.assertTrue(new ReflectionEquals(expectedMovieFirst).matches(actualMovies.get(0)));
        Assert.assertTrue(new ReflectionEquals(expectedMovieLast).matches(actualMovies.get(24)));
    }

    @DisplayName("Get three random movies")
    @Test
    void getThreeRandomMovies() {
        List<Movie> actualMovies = jdbcMovieDao.getThreeRandomMovies();

        assertEquals(3, actualMovies.size());
    }

    class SortById implements Comparator<Movie>
    {
        public int compare(Movie a, Movie b)
        {
            return a.getId() - b.getId();
        }
    }
}