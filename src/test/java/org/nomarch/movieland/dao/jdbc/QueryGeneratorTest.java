package org.nomarch.movieland.dao.jdbc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.nomarch.movieland.common.SortingOrder;
import org.nomarch.movieland.common.SortingParameter;
import org.nomarch.movieland.request.GetMovieRequest;

import static org.junit.jupiter.api.Assertions.*;

class QueryGeneratorTest {

    @DisplayName("Test appendSortingOrder(String query) is SortingOrder is not initialized")
    @Test
    void testAppendIfSortingUtilNotConfigured() {

        //when
        String returnedQuery = QueryGenerator.appendSortingOrder("SELECT id, name FROM table", new GetMovieRequest());

        //then
        assertNotNull(returnedQuery);
        assertEquals("SELECT id, name FROM table", returnedQuery);
    }

    @DisplayName("Test appendSortingOrder(String query) method with configured SortingOrder")
    @Test
    void testAppendSortingOrder() {
        //prepare
        GetMovieRequest movieRequest = GetMovieRequest.builder().sortingParameter(SortingParameter.RATING)
                .sortingOrder(SortingOrder.ASC).build();

        //when
        String returnedQuery = QueryGenerator.appendSortingOrder("SELECT id, name FROM table", movieRequest);

        //then
        assertEquals("SELECT id, name FROM table ORDER BY rating ASC", returnedQuery);
    }
}