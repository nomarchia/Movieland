package org.nomarch.movieland.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.entity.Genre;
import org.nomarch.movieland.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class GenresController {
    @Autowired
    private GenreService genreService;

    @RequestMapping(path = "/v1/genre", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public List<Genre> getAll() {
        return genreService.getAll();
    }
}
