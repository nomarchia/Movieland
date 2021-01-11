package org.nomarch.movieland.web.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
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

    public Map<String, Double> parseCurrency() {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;

        try {
            log.debug("Parsing currency rates from NBU site by url: {}", nbuJsonUrl);
            URL url = new URL(nbuJsonUrl);
            node = mapper.readTree(url);
        } catch (IOException e) {
            log.debug("Error happened while parsing currency rates from the url: {}", nbuJsonUrl);
            throw new RuntimeException("IO error happened while trying to parse currency rates");
        }

        Map<String, Double> rates = new HashMap<>();
        Double usdRate = node.get(usdJsonIndex).get("rate").asDouble();
        rates.put("USD", usdRate);
        Double eurRate = node.get(eurJsonIndex).get("rate").asDouble();
        rates.put("EUR", eurRate);

        log.debug("Returning currency rates hashmap: {}", rates);
        return rates;
    }
}
