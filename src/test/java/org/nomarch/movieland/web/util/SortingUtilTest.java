package org.nomarch.movieland.web.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.nomarch.movieland.entity.SortingOrder;

import static org.junit.jupiter.api.Assertions.*;

class SortingUtilTest {
    @DisplayName("Test appendSortingOrder(String query) is SortingUtil's fields aren't initialized")
    @Test
    void testAppendIfSortingUtilNotConfigured() {
        //prepare
        SortingUtil sortingUtil = new SortingUtil();
        //when
        String returnedQuery = sortingUtil.appendSortingOrder("SELECT id, name FROM table");
        assertNotNull(returnedQuery);
        assertEquals("SELECT id, name FROM table", returnedQuery);
    }

    @DisplayName("Test appendSortingOrder(String query) method with configured SortingUtil")
    @Test
    void testAppendSortingOrder() {
        //prepare
        SortingUtil sortingUtil = new SortingUtil();
        sortingUtil.setName("rating");
        sortingUtil.setSortingOrder(SortingOrder.ASC);
        //when
        String returnedQuery = sortingUtil.appendSortingOrder("SELECT id, name FROM table");
        //then
        assertEquals("SELECT id, name FROM table ORDER BY rating ASC", returnedQuery);
    }

    @DisplayName("Test  appendSortingOrder(String query) to NULL query param")
    @Test
    void appendSortingOrderToNullQuery() {
        //prepare
        SortingUtil sortingUtil = new SortingUtil();
        sortingUtil.setName("rating");
        sortingUtil.setSortingOrder(SortingOrder.ASC);
        //when
        assertThrows(NullPointerException.class, () -> sortingUtil.appendSortingOrder(null));
    }

    @DisplayName("Test parseSortingParams(String rating, String price)")
    @Test
    void testParseSortingParamForRatingAsc() {
        //prepare
        SortingUtil sortingUtil = new SortingUtil();
        //when
        sortingUtil.parseRawSortingParams("asc", null);
        //then
        assertEquals("rating", sortingUtil.getName());
        assertEquals(SortingOrder.ASC, sortingUtil.getSortingOrder());
    }

    @DisplayName("Test parseSortingParams(String rating, String price)")
    @Test
    void testParseSortingParamForPriceDesc() {
        //prepare
        SortingUtil sortingUtil = new SortingUtil();
        //when
        sortingUtil.parseRawSortingParams(null, "desc");
        //then
        assertEquals("price", sortingUtil.getName());
        assertEquals(SortingOrder.DESC, sortingUtil.getSortingOrder());
    }

    @DisplayName("Test parseSortingParams(rating, price) when both param values are initiated")
    @Test
    void testParseSortingBothSortingParamsHaveValues() {
        //prepare
        SortingUtil sortingUtil = new SortingUtil();
        //when
        sortingUtil = sortingUtil.parseRawSortingParams("asc", "desc");
        //then
        assertNull(sortingUtil.getName());
        assertNull(sortingUtil.getSortingOrder());
    }
}