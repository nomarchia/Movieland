package org.nomarch.movieland.common;

import lombok.Getter;

@Getter
public enum SortingParameter {
    RATING("rating"),
    PRICE("price");

    private final String paramName;

    SortingParameter(String paramName) {
        this.paramName = paramName;
    }
}
