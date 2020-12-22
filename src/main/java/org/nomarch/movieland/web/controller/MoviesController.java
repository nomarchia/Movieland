package org.nomarch.movieland.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.service.impl.DefaultMovieService;
import org.nomarch.movieland.entity.MovieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/movie", produces = MediaType.APPLICATION_JSON_VALUE)
public class MoviesController {
    @Autowired
    private DefaultMovieService movieService;

    @GetMapping
    public List<Movie> getAll(@RequestParam(required = false) String rating, @RequestParam(required = false) String price) {
        log.debug("Get request by url \"/api/v1/movie\"");
        MovieRequest movieRequest = new MovieRequest().parseRawSortingParams(rating, price);
        return movieService.findAll(movieRequest);
    }

    @GetMapping(value = "/random")
    public List<Movie> getRandom() {
        log.debug("Get request by url \"/api/v1/movie/random\"\"");

        return movieService.findRandom();
    }

    @GetMapping(value = "/genre/{genreId}")
    public List<Movie> getByGenre(@PathVariable Integer genreId, @RequestParam(required = false) String rating,
                                  @RequestParam(required = false) String price) {
        log.debug("Get request by url \"/api/v1/movie/genre/\"" + genreId);
        MovieRequest movieRequest = new MovieRequest().parseRawSortingParams(rating, price);

        return movieService.findByGenre(genreId, movieRequest);
    }

    @GetMapping(value = "/{movieId}")
    public Movie getById(@PathVariable Integer movieId) {
        log.debug("Get request by url \"/api/v1/movie/\"" + movieId);
        return movieService.findById(movieId);
    }
}
