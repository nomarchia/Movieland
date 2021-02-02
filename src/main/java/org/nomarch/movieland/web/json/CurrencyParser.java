package org.nomarch.movieland.web.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.common.currency.Currency;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j

public class CurrencyParser {
    @Value("${usd.json.index}")
    private int usdJsonIndex;
    @Value("${eur.json.index:33}")
    private int eurJsonIndex;
    /* Setter added for tests */
    @Setter
    @Value("${nbu.rates.json.url:https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json}")
    private String nbuJsonUrl;
    private List<JsonNode> currencyNodes;

    public Map<Currency, Double> getCurrencyRates() {
        if (currencyNodes == null) {
            parseCurrency();
        }

        Map<Currency, Double> rates = new HashMap<>();
        Double usdRate = currencyNodes.get(usdJsonIndex).get("rate").asDouble();
        rates.put(Currency.USD, usdRate);
        Double eurRate = currencyNodes.get(eurJsonIndex).get("rate").asDouble();
        rates.put(Currency.EUR, eurRate);

        log.debug("Returning currency rates hashmap: {}", rates);
        return rates;
    }

    private void parseCurrency() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            log.debug("Parsing currency rates from NBU site by url: {}", nbuJsonUrl);
            URL url = new URL(nbuJsonUrl);
            JsonNode node = mapper.readTree(url);

            ObjectReader reader = mapper.readerForListOf(JsonNode.class);
            currencyNodes = reader.readValue(node);
        } catch (IOException e) {
            log.debug("Error happened while parsing currency rates from the url: {}", nbuJsonUrl);
            throw new RuntimeException("IO error happened while trying to parse currency rates");
        }
    }
}
