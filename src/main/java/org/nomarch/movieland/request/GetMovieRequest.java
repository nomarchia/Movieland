package org.nomarch.movieland.request;

import lombok.*;
import org.nomarch.movieland.common.SortingOrder;
import org.nomarch.movieland.common.SortingParameter;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMovieRequest {
    private SortingParameter sortingParameter;
    private SortingOrder sortingOrder;
}
