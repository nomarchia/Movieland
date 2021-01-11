package org.nomarch.movieland.common.sorting.util;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.common.sorting.SortingOrder;
import org.nomarch.movieland.dto.MovieRequest;

import java.util.Objects;

@Slf4j
public class ParamsUtil {
    /**
     * Method assumes that only one parameter received from controller will
     * be initiated with value and the other one will be NULL.
     *
     * @return SortingUtil object with parsed and initiated parameter fields.
     * If both received parameters have NULL values or both rating and price
     * parameters have sorting values, no filter would be applied
     * and SortingUtil state stays not initiated;
     */
    public static MovieRequest parseRawSortingParams(String rating, String price) {
        MovieRequest movieRequest = new MovieRequest();
        log.debug("Parsing sorting params");
        if (Objects.nonNull(rating) && Objects.nonNull(price)) {
            log.debug("Both sorting values are initiated. No filter will be applied as sorting order");
            return movieRequest;
        }
        if (rating != null) {
            movieRequest.setSortingFieldName("rating");
            movieRequest.setSortingOrder(SortingOrder.valueOf(rating.toUpperCase()));
            log.debug("Parsed movies order by rating " + rating);
        } else if (price != null) {
            movieRequest.setSortingFieldName("price");
            movieRequest.setSortingOrder(SortingOrder.valueOf(price.toUpperCase()));
            log.debug("Parsed movies order by price " + price);
        }
        return movieRequest;
    }

    public static String appendSortingOrder(@NonNull String query, @NonNull MovieRequest movieRequest) {
        if (Objects.nonNull(movieRequest.getSortingFieldName()) && Objects.nonNull(movieRequest.getSortingOrder()) ) {
            return query + " ORDER BY " + movieRequest.getSortingFieldName() + " " + movieRequest.getSortingOrder().getOrder();
        }
        log.debug("SortingUtil parameters are not configured. No sorting order has been applied");
        return query;
    }
}
