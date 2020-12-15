package org.nomarch.movieland.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieRequestTest {
    @DisplayName("Test appendSortingOrder(String query) is SortingUtil's fields aren't initialized")
    @Test
    void testAppendIfSortingUtilNotConfigured() {
        //prepare
        MovieRequest movieRequest = new MovieRequest();
        //when
        String returnedQuery = movieRequest.appendSortingOrder("SELECT id, name FROM table");
        assertNotNull(returnedQuery);
        assertEquals("SELECT id, name FROM table", returnedQuery);
    }

    @DisplayName("Test appendSortingOrder(String query) method with configured SortingUtil")
    @Test
    void testAppendSortingOrder() {
        //prepare
        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setSortingFieldName("rating");
        movieRequest.setSortingOrder(SortingOrder.ASC);
        //when
        String returnedQuery = movieRequest.appendSortingOrder("SELECT id, name FROM table");
        //then
        assertEquals("SELECT id, name FROM table ORDER BY rating ASC", returnedQuery);
    }

    @DisplayName("Test  appendSortingOrder(String query) to NULL query param")
    @Test
    void appendSortingOrderToNullQuery() {
        //prepare
        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setSortingFieldName("rating");
        movieRequest.setSortingOrder(SortingOrder.ASC);
        //when
        assertThrows(NullPointerException.class, () -> movieRequest.appendSortingOrder(null));
    }

    @DisplayName("Test parseSortingParams(String rating, String price)")
    @Test
    void testParseSortingParamForRatingAsc() {
        //prepare
        MovieRequest movieRequest = new MovieRequest();
        //when
        movieRequest.parseRawSortingParams("asc", null);
        //then
        assertEquals("rating", movieRequest.getSortingFieldName());
        assertEquals(SortingOrder.ASC, movieRequest.getSortingOrder());
    }

    @DisplayName("Test parseSortingParams(String rating, String price)")
    @Test
    void testParseSortingParamForPriceDesc() {
        //prepare
        MovieRequest movieRequest = new MovieRequest();
        //when
        movieRequest.parseRawSortingParams(null, "desc");
        //then
        assertEquals("price", movieRequest.getSortingFieldName());
        assertEquals(SortingOrder.DESC, movieRequest.getSortingOrder());
    }

    @DisplayName("Test parseSortingParams(rating, price) when both param values are initiated")
    @Test
    void testParseSortingBothSortingParamsHaveValues() {
        //prepare
        MovieRequest movieRequest = new MovieRequest();
        //when
        movieRequest = movieRequest.parseRawSortingParams("asc", "desc");
        //then
        assertNull(movieRequest.getSortingFieldName());
        assertNull(movieRequest.getSortingOrder());
    }
}