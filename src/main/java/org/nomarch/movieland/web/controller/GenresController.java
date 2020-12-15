package org.nomarch.movieland.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.entity.Genre;
import org.nomarch.movieland.service.impl.DefaultGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/genre", produces = MediaType.APPLICATION_JSON_VALUE)
public class GenresController {
    @Autowired
    private DefaultGenreService genreService;

    @GetMapping
    public List<Genre> getAll() {
        log.debug("Get request by url \"/api/v1/genre\"");
        return genreService.findAll();
    }
}
