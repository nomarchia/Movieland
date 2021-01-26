package org.nomarch.movieland.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.entity.Country;
import org.nomarch.movieland.service.impl.DefaultCountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/country", produces = MediaType.APPLICATION_JSON_VALUE)
public class CountriesController {
    @Autowired
    private DefaultCountryService defaultCountryService;

    @GetMapping
    List<Country> getAllCountries() {
        log.debug("GET request by url \"/api/v1/country\"");
        return defaultCountryService.findAll();
    }
}
