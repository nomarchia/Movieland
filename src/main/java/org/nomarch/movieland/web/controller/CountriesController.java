package org.nomarch.movieland.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.entity.Country;
import org.nomarch.movieland.entity.UserRole;
import org.nomarch.movieland.service.CountryService;
import org.nomarch.movieland.web.Secured;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CountriesController {
    private final CountryService countryService;

    @Secured(UserRole.GUEST)
    @GetMapping(value = "/country", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Country> findAll() {
        return countryService.findAll();
    }
}
