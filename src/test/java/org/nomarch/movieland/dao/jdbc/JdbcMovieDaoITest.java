package org.nomarch.movieland.dao.jdbc;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.nomarch.movieland.MainApplicationContext;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.entity.SortingOrder;
import org.nomarch.movieland.web.util.SortingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitWebConfig (MainApplicationContext.class)
class JdbcMovieDaoITest {
    @Autowired
    private JdbcMovieDao jdbcMovieDao;

    @DisplayName("Get all movies from DB")
    @Test
    void testGetAllMovies() {
        //prepare
        Movie expectedMovieFirst = Movie.builder()
                .id(1)
                .nameNative("The Shawshank Redemption")
                .nameRussian("Побег из Шоушенка")
                .country("США")
                .year(1994)
                .rating(8.9)
                .price(123.45)
                .posterImg("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg")
                .build();
        Movie expectedMovieLast = Movie.builder()
                .id(25)
                .nameNative("Dances with Wolves")
                .nameRussian("Танцующий с волками")
                .country("США, Великобритания")
                .year(1990)
                .rating(8.00)
                .price(120.55)
                .posterImg("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg")
                .build();

        //when
        List<Movie> actualMovies = jdbcMovieDao.getAllMovies(new SortingUtil());
        actualMovies.sort(new SortMovieById());

        //then
        assertEquals(25, actualMovies.size());
        Assert.assertTrue(new ReflectionEquals(expectedMovieFirst).matches(actualMovies.get(0)));
        Assert.assertTrue(new ReflectionEquals(expectedMovieLast).matches(actualMovies.get(24)));
    }

    @DisplayName("Get all movies order by rating Asc")
    @Test
    void testGetAllMoviesSortByRatingAsc() {
        //prepare
        SortingUtil sortingUtil = new SortingUtil();
        sortingUtil.setName("rating");
        sortingUtil.setSortingOrder(SortingOrder.ASC);

        //when
        List<Movie> actualMovies = jdbcMovieDao.getAllMovies(sortingUtil);

        //then
        assertEquals(25, actualMovies.size());
        assertEquals(7.6, actualMovies.get(0).getRating());
        assertEquals(8.9, actualMovies.get(24).getRating());
    }

    @DisplayName("Get all movies order by rating Desc")
    @Test
    void testGetAllMoviesSortByRatingDesc() {
        //prepare
        SortingUtil sortingUtil = new SortingUtil();
        sortingUtil.setName("rating");
        sortingUtil.setSortingOrder(SortingOrder.DESC);

        //when
        List<Movie> actualMovies = jdbcMovieDao.getAllMovies(sortingUtil);

        //then
        assertEquals(25, actualMovies.size());
        assertEquals(8.9, actualMovies.get(0).getRating());
        assertEquals(7.6, actualMovies.get(24).getRating());
    }

    @DisplayName("Get three random movies from DB")
    @Test
    void getRandomMovies() {
        //when
        List<Movie> actualMovies = jdbcMovieDao.getRandomMovies();

        //then
        assertEquals(3, actualMovies.size());

        for (Movie aMovie : actualMovies) {
            assertNotNull(aMovie);
            assertNotEquals(0, aMovie.getId());
            assertNotNull(aMovie.getNameNative());
            assertNotNull(aMovie.getNameRussian());
            assertNotNull(aMovie.getCountry());
            assertNotEquals(0, aMovie.getYear());
            assertNotEquals(0.0, aMovie.getRating());
            assertNotEquals(0.0, aMovie.getPrice());
        }
    }

    @DisplayName("Get movies by genre from DB")
    @Test
    void testGetMoviesByGenre() {
        //prepare
        Movie expectedMovieFirst = Movie.builder()
                .id(3)
                .nameNative("Forrest Gump")
                .nameRussian("Форрест Гамп")
                .country("США")
                .year(1994)
                .rating(8.6)
                .price(200.60)
                .posterImg("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg")
                .build();
        Movie expectedMovieSecond = Movie.builder()
                .id(7)
                .nameNative("La vita è bella")
                .nameRussian("Жизнь прекрасна")
                .country("Италия")
                .year(1997)
                .rating(8.2)
                .price(145.99)
                .posterImg("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg")
                .build();
        Movie expectedMovieThird = Movie.builder()
                .id(12)
                .nameNative("Titanic")
                .nameRussian("Титаник")
                .country("США")
                .year(1997)
                .rating(7.9)
                .price(150.00)
                .posterImg("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg")
                .build();

        //when
        List<Movie> actualMovies = jdbcMovieDao.getMoviesByGenre(5, new SortingUtil());

        //then
        actualMovies.sort(new SortMovieById());

        assertEquals(actualMovies.size(), 3);

        Assert.assertTrue(new ReflectionEquals(expectedMovieFirst).matches(actualMovies.get(0)));
        Assert.assertTrue(new ReflectionEquals(expectedMovieSecond).matches(actualMovies.get(1)));
        Assert.assertTrue(new ReflectionEquals(expectedMovieThird).matches(actualMovies.get(2)));
    }

    @DisplayName("Get movies by genre order by price Asc")
    @Test
    void testGetMoviesByGenreOrderByPriceAsc() {
        //prepare
        SortingUtil sortingUtil = new SortingUtil();
        sortingUtil.setName("price");
        sortingUtil.setSortingOrder(SortingOrder.ASC);
        //when
        List<Movie> actualMovies = jdbcMovieDao.getMoviesByGenre(5, sortingUtil);

        //then
        assertEquals(actualMovies.size(), 3);
        assertEquals(145.99, actualMovies.get(0).getPrice());
        assertEquals(150.00, actualMovies.get(1).getPrice());
        assertEquals(200.6, actualMovies.get(2).getPrice());
    }

    @DisplayName("Get movies by genre order by price Desc")
    @Test
    void testGetMoviesByGenreOrderByPriceDesc() {
        SortingUtil sortingUtil = new SortingUtil();
        sortingUtil.setName("price");
        sortingUtil.setSortingOrder(SortingOrder.DESC);
        //when
        List<Movie> actualMovies = jdbcMovieDao.getMoviesByGenre(5, sortingUtil);

        //then
        assertEquals(actualMovies.size(), 3);
        assertEquals(200.6, actualMovies.get(0).getPrice());
        assertEquals(150.00, actualMovies.get(1).getPrice());
        assertEquals(145.99, actualMovies.get(2).getPrice());
    }

    @DisplayName("Get movies by non-existing genre id")
    @Test
    public void testGetMoviesByGenreOutOfRange() {
        //when
        List<Movie> actualMovies = jdbcMovieDao.getMoviesByGenre(17, new SortingUtil());

        //then
        assertEquals(0, actualMovies.size());

    }

    static class SortMovieById implements Comparator<Movie>
    {
        public int compare(Movie a, Movie b)
        {
            return a.getId() - b.getId();
        }
    }
}