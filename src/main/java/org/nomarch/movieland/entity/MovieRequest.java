package org.nomarch.movieland.entity;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@Data
public class MovieRequest {
    private String sortingFieldName;
    private SortingOrder sortingOrder;

    public String appendSortingOrder(@NonNull String query) {
        if (Objects.nonNull(sortingFieldName) && Objects.nonNull(sortingOrder) ) {
            return query + " ORDER BY " + sortingFieldName + " " + sortingOrder.getOrder();
        }
        log.debug("SortingUtil parameters are not configured. No sorting order applied");
        return query;
    }

    /**
     * Method assumes that only one parameter received from controller will
     * be initiated with value and the other one will be NULL.
     *
     * @return SortingUtil object with parsed and initiated parameter fields.
     * If both received parameters have NULL values or both rating and price
     * parameters have sorting values, no filter would be applied
     * and SortingUtil state stays not initiated;
     */
    public MovieRequest parseRawSortingParams(String rating, String price) {
        log.debug("Parsing sorting params");
        if (Objects.nonNull(rating) && Objects.nonNull(price)) {
            log.debug("Both sorting values are initiated. No filter will be applied as sorting order");
            return this;
        }
        if (rating != null) {
            this.sortingFieldName = "rating";
            this.sortingOrder = SortingOrder.valueOf(rating.toUpperCase());
            log.debug("Parsed movies order by rating " + rating);
        } else if (price != null) {
            this.sortingFieldName = "price";
            this.sortingOrder = SortingOrder.valueOf(price.toUpperCase());
            log.debug("Parsed movies order by price " + price);
        }
        return this;
    }
}
