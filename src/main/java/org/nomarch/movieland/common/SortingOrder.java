package org.nomarch.movieland.common;

import lombok.Getter;

@Getter
public enum SortingOrder {
    ASC("ASC"),
    DESC("DESC");

    private final String orderDirection;

    SortingOrder(String orderDirection) {
        this.orderDirection = orderDirection;
    }
}
