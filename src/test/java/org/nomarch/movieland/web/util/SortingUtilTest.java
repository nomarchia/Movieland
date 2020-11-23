package org.nomarch.movieland.web.util;

import org.junit.jupiter.api.Test;
import org.nomarch.movieland.entity.SortingOrder;

import static org.junit.jupiter.api.Assertions.*;

class SortingUtilTest {

    @Test
    void testConfigureWithNullParams() {
        //prepare
        SortingUtil sortingUtil = new SortingUtil();
        //when
        String actual = sortingUtil.appendSortingOrder("SELECT id, name FROM table");
        //then
        assertEquals("SELECT id, name FROM table", actual);
    }

    @Test
    void appendSortingOrder() {
        //prepare
        SortingUtil sortingUtil = new SortingUtil();
        sortingUtil.configure("rating", SortingOrder.ASC);
        //when
        String actual = sortingUtil.appendSortingOrder("SELECT id, name FROM table");
        //then
        assertEquals("SELECT id, name FROM table ORDER BY rating ASC", actual);
    }

    @Test
    void appendSortingOrderToNullQuery() {
        //prepare
        SortingUtil sortingUtil = new SortingUtil();
        sortingUtil.configure("rating", SortingOrder.ASC);
        //when
        assertThrows(NullPointerException.class, () -> {
            String actual = sortingUtil.appendSortingOrder(null);
        });
    }
}