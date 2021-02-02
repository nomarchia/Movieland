package org.nomarch.movieland.service.impl;

import lombok.RequiredArgsConstructor;
import org.nomarch.movieland.common.currency.Currency;
import org.nomarch.movieland.service.CurrencyService;
import org.nomarch.movieland.web.json.CurrencyParser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DefaultCurrencyService implements CurrencyService {
    private final CurrencyParser currencyParser;

    private Map<Currency, Double> rates;

    @Override
    public double getCurrencyRate(Currency currency) {
        return rates.get(currency);
    }

    @Scheduled(fixedRateString = "${nbu.rates.renew.interval}")
    private void getNBURates() {
        rates = currencyParser.getCurrencyRates();
    }
}
