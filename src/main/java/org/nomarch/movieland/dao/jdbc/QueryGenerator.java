package org.nomarch.movieland.dao.jdbc;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.request.GetMovieRequest;

@Slf4j
public class QueryGenerator {

    public static String appendSortingOrder(@NonNull String query, @NonNull GetMovieRequest getMovieRequest) {
        if (getMovieRequest.getSortingOrder() != null && getMovieRequest.getSortingParameter() != null) {
            return query + " ORDER BY " + getMovieRequest.getSortingParameter().getParamName() + " "
                    + getMovieRequest.getSortingOrder().getOrderDirection();
        }
        log.debug("Sorting parameters for getMovieRequest({}) are not configured. No sorting order has been applied", getMovieRequest);
        return query;
    }
}
