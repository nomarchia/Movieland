package org.nomarch.movieland.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.nomarch.movieland.MovielandApplicationContext;
import org.nomarch.movieland.common.CurrencyCode;
import org.nomarch.movieland.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import static org.junit.jupiter.api.Assertions.*;


@SpringJUnitWebConfig(value = {MovielandApplicationContext.class})
class NbuCurrencyServiceTest {
    @Autowired
    private CurrencyService currencyService;

    @DisplayName("Convert from UAH to EUR")
    @Test
    void testConvertFromUah() {
        //when
        double converted = currencyService.convert(CurrencyCode.UAH, CurrencyCode.USD, 200d);

        //then
        assertTrue(converted != 0d);
        assertTrue(converted > 5);
    }

    @DisplayName("Convert from USD to EUR")
    @Test
    void testConvertFromEurToUsd() {
        //when
        double converted = currencyService.convert(CurrencyCode.EUR, CurrencyCode.USD, 1d);

        //then
        assertTrue(converted > 1);
    }
}