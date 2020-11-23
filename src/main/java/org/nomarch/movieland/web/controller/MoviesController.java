package org.nomarch.movieland.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.entity.SortingOrder;
import org.nomarch.movieland.service.impl.DefaultMovieService;
import org.nomarch.movieland.web.util.SortingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/v1/movie", produces = MediaType.APPLICATION_JSON_VALUE)
public class MoviesController {
    @Autowired
    private DefaultMovieService movieService;

    @GetMapping
    public List<Movie> getAllMovies(@RequestParam String rating, @RequestParam String price) {
        log.debug("Get request by url \"/v1/movie\"");
        SortingUtil sortingUtil = parseSortingParams(rating, price);
        return movieService.getAllMovies(sortingUtil);
    }

    @GetMapping(value = "/random")
    public List<Movie> getRandomMovies() {
        log.debug("Get request by url \"/v1/movie/random\"\"");

        return movieService.getRandomMovies();
    }

    @GetMapping(value = "/genre/{genreId}")
    public List<Movie> getMoviesByGenre(@PathVariable Integer genreId, @RequestParam String rating, @RequestParam String price) {
        log.debug("Get request by url \"/v1/movie/genre/\"" + genreId);
        SortingUtil sortingUtil = parseSortingParams(rating, price);

        return movieService.getMoviesByGenre(genreId, sortingUtil);
    }

    //package-private for tests
    SortingUtil parseSortingParams(String rating, String price) {
        log.debug("Parsing sorting params");
        SortingUtil sortingUtil = new SortingUtil();
        if (rating != null) {
            sortingUtil.setName("rating");
            sortingUtil.setSortingOrder(SortingOrder.valueOf(rating.toUpperCase()));
            log.debug("Parsed movies order by rating " + rating);
        } else if (price != null) {
            sortingUtil.setName("price");
            sortingUtil.setSortingOrder(SortingOrder.valueOf(price.toUpperCase()));
            log.debug("Parsed movies order by price " + price);
        }
        return sortingUtil;
    }
}
