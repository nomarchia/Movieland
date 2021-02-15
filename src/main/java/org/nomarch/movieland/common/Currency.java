package org.nomarch.movieland.common;

import lombok.Getter;

public enum Currency {
    USD("USD"),
    EUR("EUR"),
    UAH("UAH");

    @Getter
    private final String currencyCode;

    Currency(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public static Currency getByName(String currencyName) {
        for (Currency currency : values()) {
            if (currency.currencyCode.equals(currencyName)) {
                return currency;
            }
        }

        throw new IllegalArgumentException("No Currency found by the name: " + currencyName);
    }
}
