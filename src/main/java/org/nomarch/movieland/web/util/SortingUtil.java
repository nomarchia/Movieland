package org.nomarch.movieland.web.util;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.entity.SortingOrder;

import java.util.Objects;

@Slf4j
@Data
public class SortingUtil {
    private String name;
    private SortingOrder sortingOrder;

    public void configure(@NonNull String name, @NonNull SortingOrder sortingOrder) {
        this.name = name;
        this.sortingOrder = sortingOrder;
    }

    public String appendSortingOrder(@NonNull String query) {
        if (Objects.nonNull(name) && Objects.nonNull(sortingOrder) ) {
            return query + " ORDER BY " + name + " " + sortingOrder.getOrder();
        }
        return query;
    }

    SortingUtil parseSortingParams(String rating, String price) {
        log.debug("Parsing sorting params");
        SortingUtil sortingUtil = new SortingUtil();
        if (rating != null) {
            this.setName("rating");
            this.setSortingOrder(SortingOrder.valueOf(rating.toUpperCase()));
            log.debug("Parsed movies order by rating " + rating);
        } else if (price != null) {
            this.setName("price");
            this.setSortingOrder(SortingOrder.valueOf(price.toUpperCase()));
            log.debug("Parsed movies order by price " + price);
        }
        return this;
    }
}
