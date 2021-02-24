package org.nomarch.movieland.common;

import lombok.Getter;

public enum CurrencyCode {
    USD("USD"),
    EUR("EUR"),
    UAH("UAH");

    @Getter
    private final String currencyCode;

    CurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public static CurrencyCode getByName(String currencyName) {
        for (CurrencyCode currencyCode : values()) {
            if (currencyCode.currencyCode.equals(currencyName)) {
                return currencyCode;
            }
        }

        throw new IllegalArgumentException("No currency code found by the name: " + currencyName);
    }
}
