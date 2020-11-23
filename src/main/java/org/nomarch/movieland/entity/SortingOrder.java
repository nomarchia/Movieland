package org.nomarch.movieland.entity;

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
