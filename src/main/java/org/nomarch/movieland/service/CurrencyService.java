package org.nomarch.movieland.service;

import org.nomarch.movieland.common.currency.Currency;

public interface CurrencyService {
    double getCurrencyRate(Currency currency);
}
