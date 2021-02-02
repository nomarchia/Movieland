package org.nomarch.movieland.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.entity.Genre;
import org.nomarch.movieland.service.GenreService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/genre", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GenresController {
    private final GenreService genreService;

    @GetMapping
    public List<Genre> getAll() {
        log.debug("GET request by url \"/api/v1/genre\"");
        return genreService.findAll();
    }
}
