package org.nomarch.movieland.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.common.Currency;
import org.nomarch.movieland.service.CurrencyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class NbuCurrencyService implements CurrencyService {
    @Value("${usd.json.index}")
    private int usdJsonIndex;
    @Value("${eur.json.index:33}")
    private int eurJsonIndex;
    /* Setter added for tests */
    @Setter
    @Value("${nbu.rates.json.url}")
    private String nbuJsonUrl;

    private Map<String, Double> currencyRates;

    @Override
    public double getCurrencyRate(Currency currency) {
        if (currencyRates == null) {
            parseCurrency();
        }
        return currencyRates.get(currency.getCurrencyCode());
    }

    @Scheduled(fixedRateString = "${nbu.rates.renew.interval}")
    private void parseCurrency() {
        currencyRates = enrichAllCurrenciesToMap();
    }

    private Map<String, Double> enrichAllCurrenciesToMap() {
        List<JsonNode> currencyNodes = getAllCurrenciesFromNbu();
        Map<String, Double> rates = new HashMap<>();

        for (JsonNode currencyNode : currencyNodes) {
            rates.put(currencyNode.get("cc").asText(), currencyNode.get("rate").asDouble());
        }

        return rates;
    }

    private List<JsonNode> getAllCurrenciesFromNbu() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            log.debug("Parsing currency rates from NBU site by url: {}", nbuJsonUrl);
            URL url = new URL(nbuJsonUrl);
            JsonNode node = mapper.readTree(url);

            ObjectReader reader = mapper.readerForListOf(JsonNode.class);
            return reader.readValue(node);
        } catch (IOException e) {
            log.debug("Error happened while parsing currency rates from the url: {}", nbuJsonUrl);
            throw new RuntimeException("IO error happened while trying to parse currency rates");
        }
    }
}
