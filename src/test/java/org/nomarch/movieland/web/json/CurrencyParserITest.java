package org.nomarch.movieland.web.json;

import org.junit.jupiter.api.*;
import org.nomarch.movieland.RootApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitWebConfig(value = RootApplicationContext.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CurrencyParserITest {
    @Autowired
    CurrencyParser currencyParser;

    @Test
    @DisplayName("Test parsing NBU rates json from official site")
    @Order(1)
    void testParseCurrency() {
        //when
        Map<String, Double> rates = currencyParser.parseCurrency();

        //then
        assertNotNull(rates);
        assertEquals(2, rates.size());
        assertTrue(rates.containsKey("USD"));
        assertTrue(rates.containsKey("EUR"));
        assertTrue(rates.get("USD") > 00.0);
        assertTrue(rates.get("EUR") > 00.0);
    }

    @Test
    @DisplayName("Test parsing currency rates json from incorrect url")
    @Order(2)
    void testParseCurrencyWithIncorrectURL() {
        //prepare
        currencyParser.setNbuJsonUrl("incorrectUrl");

        //when
        assertThrows(RuntimeException.class, () -> currencyParser.parseCurrency());
    }
}