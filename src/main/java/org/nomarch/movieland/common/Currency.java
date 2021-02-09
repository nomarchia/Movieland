package org.nomarch.movieland.common;

public enum Currency {
    USD("USD"),
    EUR("EUR"),
    UAH("UAH");

    private String currencyName;

    Currency(String currencyName) {
        this.currencyName = currencyName;
    }

    public static Currency getByName(String currencyName) {
        for (Currency currency : values()) {
            if (currency.currencyName.equals(currencyName)) {
                return currency;
            }
        }

        throw new IllegalArgumentException("No Currency found by the name: " + currencyName);
    }
}
