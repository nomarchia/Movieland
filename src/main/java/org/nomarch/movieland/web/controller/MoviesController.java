package org.nomarch.movieland.web.controller;

import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.service.impl.DefaultMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/movie", produces = MediaType.APPLICATION_JSON_VALUE)
public class MoviesController {
    @Autowired
    private DefaultMovieService movieService;

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping(value = "/random")
    public List<Movie> getRandomMovies() {
        return movieService.getRandomMovies();
    }

    @GetMapping(value = "/genre/{genreId}")
    public List<Movie> getMoviesByGenre(@PathVariable Integer genreId) {
        return movieService.getMoviesByGenre(genreId);
    }
}
