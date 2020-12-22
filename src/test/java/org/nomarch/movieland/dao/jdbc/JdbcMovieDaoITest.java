package org.nomarch.movieland.dao.jdbc;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.nomarch.movieland.RootApplicationContext;
import org.nomarch.movieland.TestContext;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.entity.SortingOrder;
import org.nomarch.movieland.entity.MovieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DBRider
@DBUnit(caseSensitiveTableNames = false, caseInsensitiveStrategy = Orthography.LOWERCASE)
@SpringJUnitWebConfig(value = {TestContext.class, RootApplicationContext.class})
@DataSet(value = {"movies.xml", "genres.xml", "movie_to_genre.xml"})
class JdbcMovieDaoITest {
    @Autowired
    private JdbcMovieDao jdbcMovieDao;

    @DisplayName("Get all movies from DB")
    @Test
    void testFindAll() {
        //prepare
        Movie expectedMovieFirst = Movie.builder()
                .id(1)
                .nameNative("The Shawshank Redemption")
                .nameRussian("Побег из Шоушенка")
                .country("США")
                .year(1994)
                .rating(8.9)
                .price(123.45)
                .posterImg("image1.jpg")
                .build();
        Movie expectedMovieLast = Movie.builder()
                .id(5)
                .nameNative("Dances with Wolves")
                .nameRussian("Танцующий с волками")
                .country("США, Великобритания")
                .year(1990)
                .rating(8.00)
                .price(120.55)
                .posterImg("image5.jpg")
                .build();

        //when
        List<Movie> actualMovies = jdbcMovieDao.findAll(new MovieRequest());

        //then
        assertEquals(5, actualMovies.size());
        assertTrue(new ReflectionEquals(expectedMovieFirst).matches(actualMovies.get(0)));
        assertTrue(new ReflectionEquals(expectedMovieLast).matches(actualMovies.get(4)));
    }

    @DisplayName("Get all movies order by rating Asc")
    @Test
    void testFindAllSortByRatingAsc() {
        //prepare
        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setSortingFieldName("rating");
        movieRequest.setSortingOrder(SortingOrder.ASC);

        //when
        List<Movie> actualMovies = jdbcMovieDao.findAll(movieRequest);

        //then
        assertEquals(5, actualMovies.size());
        assertEquals(7.9, actualMovies.get(0).getRating());
        assertEquals(8.9, actualMovies.get(4).getRating());
    }

    @DisplayName("Get all movies order by rating Desc")
    @Test
    void testGetAllMoviesSortByRatingDesc() {
        //prepare
        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setSortingFieldName("rating");
        movieRequest.setSortingOrder(SortingOrder.DESC);

        //when
        List<Movie> actualMovies = jdbcMovieDao.findAll(movieRequest);

        //then
        assertEquals(5, actualMovies.size());
        assertEquals(8.9, actualMovies.get(0).getRating());
        assertEquals(7.9, actualMovies.get(4).getRating());
    }

    @DisplayName("Get three random movies from DB")
    @Test
    void testFindRandom() {
        //when
        List<Movie> actualMovies = jdbcMovieDao.findRandom(3);

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
    void testFindByGenre() {
        //prepare
        Movie expectedMovieFirst = Movie.builder()
                .id(2)
                .nameNative("Forrest Gump")
                .nameRussian("Форрест Гамп")
                .country("США")
                .year(1994)
                .rating(8.6)
                .price(200.60)
                .posterImg("image2.jpg")
                .build();
        Movie expectedMovieSecond = Movie.builder()
                .id(3)
                .nameNative("La vita è bella")
                .nameRussian("Жизнь прекрасна")
                .country("Италия")
                .year(1997)
                .rating(8.2)
                .price(145.99)
                .posterImg("image3.jpg")
                .build();
        Movie expectedMovieThird = Movie.builder()
                .id(4)
                .nameNative("Titanic")
                .nameRussian("Титаник")
                .country("США")
                .year(1997)
                .rating(7.9)
                .price(150.00)
                .posterImg("image4.jpg")
                .build();

        //when
        List<Movie> actualMovies = jdbcMovieDao.findByGenre(5, new MovieRequest());

        //then
        assertEquals(actualMovies.size(), 3);
        Assert.assertTrue(new ReflectionEquals(expectedMovieFirst).matches(actualMovies.get(0)));
        Assert.assertTrue(new ReflectionEquals(expectedMovieSecond).matches(actualMovies.get(1)));
        Assert.assertTrue(new ReflectionEquals(expectedMovieThird).matches(actualMovies.get(2)));
    }

    @DisplayName("Get movies by genre order by price Asc")
    @Test
    void testFindByGenreOrderByPriceAsc() {
        //prepare
        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setSortingFieldName("price");
        movieRequest.setSortingOrder(SortingOrder.ASC);
        //when
        List<Movie> actualMovies = jdbcMovieDao.findByGenre(5, movieRequest);

        //then
        assertEquals(actualMovies.size(), 3);
        assertEquals(145.99, actualMovies.get(0).getPrice());
        assertEquals(150.00, actualMovies.get(1).getPrice());
        assertEquals(200.6, actualMovies.get(2).getPrice());
    }

    @DisplayName("Get movies by genre order by price Desc")
    @Test
    void testFindByGenreOrderByPriceDesc() {
        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setSortingFieldName("price");
        movieRequest.setSortingOrder(SortingOrder.DESC);
        //when
        List<Movie> actualMovies = jdbcMovieDao.findByGenre(5, movieRequest);

        //then
        assertEquals(actualMovies.size(), 3);
        assertEquals(200.6, actualMovies.get(0).getPrice());
        assertEquals(150.00, actualMovies.get(1).getPrice());
        assertEquals(145.99, actualMovies.get(2).getPrice());
    }

    @DisplayName("Get movies by non-existing genre id")
    @Test
    public void testFindByGenreOutOfRange() {
        //when
        List<Movie> actualMovies = jdbcMovieDao.findByGenre(17, new MovieRequest());

        //then
        assertEquals(0, actualMovies.size());
    }

    @DisplayName("Get a movie by id")
    @Test
    void testFindById() {
        //prepare
        Movie expectedMovie = Movie.builder()
                .id(1)
                .nameNative("The Shawshank Redemption")
                .nameRussian("Побег из Шоушенка")
                .country("США")
                .year(1994)
                .description("a")
                .rating(8.9)
                .price(123.45)
                .posterImg("image1.jpg")
                .build();
        //when
        Movie actualMovie = jdbcMovieDao.findById(1);
        assertEquals(expectedMovie, actualMovie);
    }
}