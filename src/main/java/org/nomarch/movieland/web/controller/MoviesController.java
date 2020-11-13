package org.nomarch.movieland.web.controller;

import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/movie", produces = MediaType.APPLICATION_JSON_VALUE)
public class MoviesController {
    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<Movie> getAll() {
        return movieService.getAll();
    }
}
