package org.nomarch.movieland.web.json;

import org.junit.jupiter.api.*;
import org.nomarch.movieland.RootApplicationContext;
import org.nomarch.movieland.common.currency.Currency;
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
    @DisplayName("Test get USD and EUR currency rates after parsing NBU json from official site")
    @Order(1)
    void testGetCurrencyRate() {
        //when
        Map<Currency, Double> rates = currencyParser.getCurrencyRates();

        //then
        assertNotNull(rates);
        assertEquals(2, rates.size());
        assertTrue(rates.containsKey(Currency.USD));
        assertTrue(rates.containsKey(Currency.EUR));
        assertTrue(rates.get(Currency.USD) > 00.0);
        assertTrue(rates.get(Currency.EUR) > 00.0);
    }

    @Test
    @DisplayName("Test parsing currency rates json from incorrect url")
    @Order(2)
    void testParseCurrencyWithIncorrectURL() {
        //prepare
        currencyParser = new CurrencyParser();
        currencyParser.setNbuJsonUrl("incorrectUrl");

        //when
        assertThrows(RuntimeException.class, () -> currencyParser.getCurrencyRates());
    }
}