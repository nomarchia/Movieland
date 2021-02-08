package org.nomarch.movieland.common;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum SortingOrder {
    ASC("ASC"),
    DESC("DESC"),
    NULL("NULL");

    private String order;
    @Setter
    private String parameterName;

    SortingOrder(String order) {
        this.order = order;
    }
}
