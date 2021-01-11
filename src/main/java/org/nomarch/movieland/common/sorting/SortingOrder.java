package org.nomarch.movieland.common.sorting;

import lombok.Getter;

@Getter
public enum SortingOrder {
    ASC("ASC"),
    DESC("DESC");

    private String order;

    SortingOrder(String order) {
        this.order = order;
    }
}
