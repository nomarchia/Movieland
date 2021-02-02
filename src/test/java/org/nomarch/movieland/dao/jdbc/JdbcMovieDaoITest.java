package org.nomarch.movieland.dao.jdbc;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.*;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.nomarch.movieland.RootApplicationContext;
import org.nomarch.movieland.TestContext;
import org.nomarch.movieland.dao.MovieDao;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.common.sorting.SortingOrder;
import org.nomarch.movieland.dto.movie.MovieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DBRider
@DBUnit(caseSensitiveTableNames = false, caseInsensitiveStrategy = Orthography.LOWERCASE)
@SpringJUnitWebConfig(value = {TestContext.class, RootApplicationContext.class})
class JdbcMovieDaoITest {
    @Autowired
    private MovieDao movieDao;

    @DisplayName("Get all movies from DB")
    @Test
    @DataSet(value = "movies.xml", cleanBefore = true)
    void testFindAll() {
        //prepare
        Movie expectedMovieFirst = Movie.builder()
                .id(1L)
                .nameNative("The Shawshank Redemption")
                .nameRussian("Побег из Шоушенка")
                .yearOfRelease(1994)
                .rating(8.9)
                .price(123.45)
                .picturePath("image1.jpg")
                .build();
        Movie expectedMovieLast = Movie.builder()
                .id(5L)
                .nameNative("Dances with Wolves")
                .nameRussian("Танцующий с волками")
                .yearOfRelease(1990)
                .rating(8.00)
                .price(120.55)
                .picturePath("image5.jpg")
                .build();

        //when
        List<Movie> actualMovies = movieDao.findAll(new MovieRequest());

        //then
        assertEquals(5, actualMovies.size());
        assertTrue(new ReflectionEquals(expectedMovieFirst).matches(actualMovies.get(0)));
        assertTrue(new ReflectionEquals(expectedMovieLast).matches(actualMovies.get(4)));
    }

    @DisplayName("Get all movies order by rating Asc")
    @Test
    @DataSet(value = "movies.xml", cleanBefore = true)
    void testFindAllSortByRatingAsc() {
        //prepare
        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setSortingFieldName("rating");
        movieRequest.setSortingOrder(SortingOrder.ASC);

        //when
        List<Movie> actualMovies = movieDao.findAll(movieRequest);

        //then
        assertEquals(5, actualMovies.size());
        assertEquals(7.9, actualMovies.get(0).getRating());
        assertEquals(8.9, actualMovies.get(4).getRating());
    }

    @DisplayName("Get all movies order by rating Desc")
    @Test
    @DataSet(value = "movies.xml", cleanBefore = true)
    void testGetAllMoviesSortByRatingDesc() {
        //prepare
        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setSortingFieldName("rating");
        movieRequest.setSortingOrder(SortingOrder.DESC);

        //when
        List<Movie> actualMovies = movieDao.findAll(movieRequest);

        //then
        assertEquals(5, actualMovies.size());
        assertEquals(8.9, actualMovies.get(0).getRating());
        assertEquals(7.9, actualMovies.get(4).getRating());
    }

    @DisplayName("Get three random movies from DB")
    @Test
    @DataSet(value = "movies.xml", cleanBefore = true)
    void testFindRandom() {
        //when
        List<Movie> actualMovies = movieDao.findRandom(3);

        //then
        assertEquals(3, actualMovies.size());

        for (Movie aMovie : actualMovies) {
            assertNotNull(aMovie);
            assertNotEquals(0, aMovie.getId());
            assertNotNull(aMovie.getNameNative());
            assertNotNull(aMovie.getNameRussian());
            assertNotEquals(0, aMovie.getYearOfRelease());
            assertNotEquals(0.0, aMovie.getRating());
            assertNotEquals(0.0, aMovie.getPrice());
        }
    }

    @DisplayName("Get movies by genre from DB")
    @Test
    @DataSet(value = "movies_genres_and_movie_to_genre.xml", cleanBefore = true)
    void testFindByGenre() {
        //prepare
        Movie expectedMovieFirst = Movie.builder()
                .id(2L)
                .nameNative("Forrest Gump")
                .nameRussian("Форрест Гамп")
                .yearOfRelease(1994)
                .rating(8.6)
                .price(200.60)
                .picturePath("image2.jpg")
                .build();
        Movie expectedMovieSecond = Movie.builder()
                .id(3L)
                .nameNative("La vita è bella")
                .nameRussian("Жизнь прекрасна")
                .yearOfRelease(1997)
                .rating(8.2)
                .price(145.99)
                .picturePath("image3.jpg")
                .build();
        Movie expectedMovieThird = Movie.builder()
                .id(4L)
                .nameNative("Titanic")
                .nameRussian("Титаник")
                .yearOfRelease(1997)
                .rating(7.9)
                .price(150.00)
                .picturePath("image4.jpg")
                .build();

        //when
        List<Movie> actualMovies = movieDao.findByGenre(5, new MovieRequest());

        //then
        assertEquals(3, actualMovies.size());
        assertTrue(new ReflectionEquals(expectedMovieFirst).matches(actualMovies.get(0)));
        assertTrue(new ReflectionEquals(expectedMovieSecond).matches(actualMovies.get(1)));
        assertTrue(new ReflectionEquals(expectedMovieThird).matches(actualMovies.get(2)));
    }

    @DisplayName("Get movies by genre order by price Asc")
    @Test
    @DataSet(value = "movies_genres_and_movie_to_genre.xml", cleanBefore = true)
    void testFindByGenreOrderByPriceAsc() {
        //prepare
        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setSortingFieldName("price");
        movieRequest.setSortingOrder(SortingOrder.ASC);
        //when
        List<Movie> actualMovies = movieDao.findByGenre(5, movieRequest);

        //then
        assertEquals(3, actualMovies.size());
        assertEquals(145.99, actualMovies.get(0).getPrice());
        assertEquals(150.00, actualMovies.get(1).getPrice());
        assertEquals(200.6, actualMovies.get(2).getPrice());
    }

    @DisplayName("Get movies by genre order by price Desc")
    @Test
    @DataSet(value = "movies_genres_and_movie_to_genre.xml", cleanBefore = true)
    void testFindByGenreOrderByPriceDesc() {
        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setSortingFieldName("price");
        movieRequest.setSortingOrder(SortingOrder.DESC);
        //when
        List<Movie> actualMovies = movieDao.findByGenre(5, movieRequest);

        //then
        assertEquals(actualMovies.size(), 3);
        assertEquals(200.6, actualMovies.get(0).getPrice());
        assertEquals(150.00, actualMovies.get(1).getPrice());
        assertEquals(145.99, actualMovies.get(2).getPrice());
    }

    @DisplayName("Get movies by non-existing genre id")
    @Test
    @DataSet(value = "movies_genres_and_movie_to_genre.xml", cleanBefore = true)
    public void testFindByGenreOutOfRange() {
        //when
        List<Movie> actualMovies = movieDao.findByGenre(17, new MovieRequest());

        //then
        assertEquals(0, actualMovies.size());
    }

    @DisplayName("Get a movie by id")
    @Test
    @DataSet(value = "movies.xml", cleanBefore = true)
    void testFindById() {
        //prepare
        Movie expectedMovie = Movie.builder()
                .id(1L)
                .nameNative("The Shawshank Redemption")
                .nameRussian("Побег из Шоушенка")
                .yearOfRelease(1994)
                .description("a")
                .rating(8.9)
                .price(123.45)
                .picturePath("image1.jpg")
                .build();
        //when
        Movie actualMovie = movieDao.findById(1L);
        assertEquals(expectedMovie, actualMovie);
    }

    @DisplayName("Add new movie to DB")
    @Test
    @DataSet(value = "movies_genres_countries_and_many_to_many_tables.xml", cleanBefore = true)
    @ExpectedDataSet(value = "tables_after_adding_new_movie.xml")
    void testAddNew() {
        //prepare
        Movie newMovie = Movie.builder().nameNative("New movie").nameRussian("Новый фильм").yearOfRelease(2020)
                .description("Description").price(69.99).picturePath("image6.jpg")
                .genres(new int[]{1, 2}).countries(new int[]{1, 2}).build();

        //when
        movieDao.add(newMovie);
    }

    @DisplayName("Update info of existing movie in DB without changed genres/countries")
    @Test
    @DataSet(value = "movies.xml", cleanBefore = true)
    @ExpectedDataSet(value = "movies_after_movie_edited.xml")
    void testEditMovie() {
        //prepare
        Movie updatedMovie = Movie.builder().id(2L).nameRussian("Изменное название").price(200.1).picturePath("newPoster.jpg").build();

        //when
        movieDao.edit(updatedMovie);
    }

    @DisplayName("Update info of existing movie in DB with changed genres/countries")
    @Test
    @DataSet(value = "movies_genres_countries_and_many_to_many_tables.xml", cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet(value = "tables_after_movie_has_been_edited.xml")
    void testEditMovieWithGenresAndCountries() {
        //prepare
        Movie updatedMovie = Movie.builder().id(3L).nameRussian("Изменное название").price(200.1).picturePath("newPoster.jpg")
                .genres(new int[]{1, 2}).countries(new int[]{1, 2}).build();

        //when
        movieDao.edit(updatedMovie);
    }
}