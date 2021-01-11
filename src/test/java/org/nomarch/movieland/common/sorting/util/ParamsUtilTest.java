package org.nomarch.movieland.common.sorting.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.nomarch.movieland.common.sorting.SortingOrder;
import org.nomarch.movieland.common.sorting.util.ParamsUtil;
import org.nomarch.movieland.dto.MovieRequest;

import static org.junit.jupiter.api.Assertions.*;

class ParamsUtilTest {
    @DisplayName("Test appendSortingOrder(String query) is SortingUtil's fields aren't initialized")
    @Test
    void testAppendIfSortingUtilNotConfigured() {
        //prepare
        MovieRequest movieRequest = new MovieRequest();
        //when
        String returnedQuery = ParamsUtil.appendSortingOrder("SELECT id, name FROM table", movieRequest);
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
        String returnedQuery = ParamsUtil.appendSortingOrder("SELECT id, name FROM table", movieRequest);
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
        assertThrows(NullPointerException.class, () -> ParamsUtil.appendSortingOrder(null, movieRequest));
    }

    @DisplayName("Test parseSortingParams(String rating, String price)")
    @Test
    void testParseSortingParamForRatingAsc() {
        //when
        MovieRequest movieRequest = ParamsUtil.parseRawSortingParams("asc", null);
        //then
        assertEquals("rating", movieRequest.getSortingFieldName());
        assertEquals(SortingOrder.ASC, movieRequest.getSortingOrder());
    }

    @DisplayName("Test parseSortingParams(String rating, String price)")
    @Test
    void testParseSortingParamForPriceDesc() {
        //when
        MovieRequest movieRequest = ParamsUtil.parseRawSortingParams(null, "desc");
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
        movieRequest = ParamsUtil.parseRawSortingParams("asc", "desc");
        //then
        assertNull(movieRequest.getSortingFieldName());
        assertNull(movieRequest.getSortingOrder());
    }
}