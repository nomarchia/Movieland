package org.nomarch.movieland.dto.movie;

import lombok.Data;
import org.nomarch.movieland.common.sorting.SortingOrder;

@Data
public class MovieRequest {
    private String sortingFieldName;
    private SortingOrder sortingOrder;
}
