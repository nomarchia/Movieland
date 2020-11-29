package org.nomarch.movieland.web.util;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.entity.SortingOrder;

import java.util.Objects;

/**
 * Util class to parse sorting order parameters
 * and then appends it to the SQL query.
 *
 * @author Nomarch (nomarchia2@gmail.com)
 */

@Slf4j
@Data
public class SortingUtil {
    private String name;
    private SortingOrder sortingOrder;

    public String appendSortingOrder(@NonNull String query) {
        if (Objects.nonNull(name) && Objects.nonNull(sortingOrder) ) {
            return query + " ORDER BY " + name + " " + sortingOrder.getOrder();
        }
        log.debug("SortingUtil parameters are not configured. No sorting order applied");
        return query;
    }

    /**
     * Method assumes that only one parameter received from controller will
     * be initiated with value and the other one will be NULL.
     *
     * @return SortingUtil object with parsed and initiated parameter fields.
     * If both recieved parameters have NULL values or both rating and price
     * parameters have sorting values, no filter would be applied
     * and SortingUtil state stays not initiated;
     */
    public SortingUtil parseRawSortingParams(String rating, String price) {
        log.debug("Parsing sorting params");
        if (Objects.nonNull(rating) && Objects.nonNull(price)) {
            log.debug("Both sorting values are initiated. No filter will be applied as sorting order");
            return this;
        }
        if (rating != null) {
            this.name = "rating";
            this.sortingOrder = SortingOrder.valueOf(rating.toUpperCase());
            log.debug("Parsed movies order by rating " + rating);
        } else if (price != null) {
            this.name = "price";
            this.sortingOrder = SortingOrder.valueOf(price.toUpperCase());
            log.debug("Parsed movies order by price " + price);
        }
        return this;
    }
}
