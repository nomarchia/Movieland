package org.nomarch.movieland.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.nomarch.movieland.common.SortingOrder;
import org.nomarch.movieland.entity.Movie;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class QueryGeneratorTest {

    @DisplayName("Generate update query for updated movie")
    @Test
    void testFormUpdateQueryForFullyUpdatedMovie() {
        //prepare
        Movie updatedMovie = Movie.builder().id(1L).nameNative("Changed title").nameRussian("Изменное название")
                .yearOfRelease(2020).description("new description").price(200.1).picturePath("newPoster.jpg").build();
        String expectedQuery = "UPDATE movies SET name_native = :name_native, name_russian = :name_russian, year = :year, " +
                "description = :description, price = :price, poster_img = :poster_img WHERE id = :movie_id";

        //when
        Map<String, Object> mapWithParams = QueryGenerator.formMovieUpdateQuery(updatedMovie);

        //then
        assertEquals(expectedQuery, mapWithParams.get("query"));

        MapSqlParameterSource parameterSource = (MapSqlParameterSource) mapWithParams.get("parameterSource");
        assertEquals("Changed title", parameterSource.getValue("name_native"));
        assertEquals("Изменное название", parameterSource.getValue("name_russian"));
        assertEquals(2020, parameterSource.getValue("year"));
        assertEquals("new description", parameterSource.getValue("description"));
        assertEquals(200.1, parameterSource.getValue("price"));
        assertEquals("newPoster.jpg", parameterSource.getValue("poster_img"));
    }

    @DisplayName("Generate update query for partly updated movie")
    @Test
    void testFormUpdateQueryForPartlyUpdatedMovie() {
        //prepare
        Movie updatedMovie = Movie.builder().id(1L).nameRussian("Изменное название").price(200.1).picturePath("newPoster.jpg").build();
        String expectedQuery = "UPDATE movies SET name_russian = :name_russian, price = :price, poster_img = :poster_img WHERE id = :movie_id";

        //when
        Map<String, Object> mapWithParams = QueryGenerator.formMovieUpdateQuery(updatedMovie);

        //then
        assertEquals(expectedQuery, mapWithParams.get("query"));

        MapSqlParameterSource parameterSource = (MapSqlParameterSource) mapWithParams.get("parameterSource");
        assertEquals("Изменное название", parameterSource.getValue("name_russian"));
        assertEquals(200.1, parameterSource.getValue("price"));
        assertEquals("newPoster.jpg", parameterSource.getValue("poster_img"));
    }

    @DisplayName("Test appendSortingOrder(String query) is SortingOrder is not initialized")
    @Test
    void testAppendIfSortingUtilNotConfigured() {

        //when
        String returnedQuery = QueryGenerator.appendSortingOrder("SELECT id, name FROM table", SortingOrder.NULL);

        //then
        assertNotNull(returnedQuery);
        assertEquals("SELECT id, name FROM table", returnedQuery);
    }

    @DisplayName("Test appendSortingOrder(String query) method with configured SortingOrder")
    @Test
    void testAppendSortingOrder() {
        //prepare
        SortingOrder sortingOrder = SortingOrder.ASC;
        sortingOrder.setParameterName("rating");

        //when
        String returnedQuery = QueryGenerator.appendSortingOrder("SELECT id, name FROM table", sortingOrder);

        //then
        assertEquals("SELECT id, name FROM table ORDER BY rating ASC", returnedQuery);
    }
}