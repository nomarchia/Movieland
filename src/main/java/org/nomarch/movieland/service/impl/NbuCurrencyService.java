package org.nomarch.movieland.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.common.Currency;
import org.nomarch.movieland.common.CurrencyCode;
import org.nomarch.movieland.service.CurrencyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class NbuCurrencyService implements CurrencyService {
    /* Setter added for tests */
    @Setter(AccessLevel.PACKAGE)
    @Value("${nbu.rates.json.url}")
    private String nbuJsonUrl;

    private Map<String, Double> currencyRates;

    @Override
    public double convert(CurrencyCode from, CurrencyCode to, Double amount) {
        if (currencyRates == null) {
            updateCurrencyRates();
        }

        if (from == CurrencyCode.UAH) {
            return amount / currencyRates.get(to.getCurrencyCode());
        }

        return currencyRates.get(from.getCurrencyCode()) / currencyRates.get(to.getCurrencyCode()) * amount;
    }


    @Scheduled(cron = "${nbu.rates.renew.time.cron}")
    private void updateCurrencyRates() {
        currencyRates = enrichCurrencyRatesMap();
    }

    private Map<String, Double> enrichCurrencyRatesMap() {
        List<Currency> currencies = getAllCurrenciesFromNbu();
        Map<String, Double> rates = new HashMap<>();

        for (Currency currency : currencies) {
            rates.put(currency.getCc(), currency.getRate());
        }

        return rates;
    }

    private List<Currency> getAllCurrenciesFromNbu() {
        RestTemplate restTemplate = new RestTemplate();

        Currency[] currencies = restTemplate.getForObject(nbuJsonUrl, Currency[].class);
        return Arrays.asList(currencies);
    }
}
