package org.nomarch.movieland.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.common.Currency;
import org.nomarch.movieland.common.SortingOrder;
import org.nomarch.movieland.request.SaveMovieRequest;
import org.nomarch.movieland.dto.FullMovieDto;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.security.SecurityService;
import org.nomarch.movieland.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/movie")
@RequiredArgsConstructor
public class MoviesController {
    private final MovieService movieService;
    private final SecurityService securityService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> getAll(@RequestParam(name = "rating", required = false) SortingOrder ratingOrder,
                              @RequestParam(name = "price", required = false) SortingOrder priceOrder) {
        log.debug("GET request by url \"/api/v1/movie\"");
        SortingOrder sortingOrder = validateSortingParams(ratingOrder, priceOrder);
        return movieService.findAll(sortingOrder);
    }

    @GetMapping(value = "/random", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> getRandom() {
        log.debug("GET request by url \"/api/v1/movie/random\"\"");

        return movieService.findRandom();
    }

    @GetMapping(value = "/genre/{genreId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> getByGenre(@PathVariable Integer genreId,
                                  @RequestParam(name = "rating", required = false) SortingOrder ratingOrder,
                                  @RequestParam(name = "price", required = false) SortingOrder priceOrder) {
        log.debug("GET request by url \"/api/v1/movie/genre/\"{}", genreId);
        SortingOrder sortingOrder = validateSortingParams(ratingOrder, priceOrder);

        return movieService.findByGenre(genreId, sortingOrder);
    }

    @GetMapping(value = "/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FullMovieDto getById(@PathVariable Long movieId, @RequestParam(required = false) Currency currency) {
        log.debug("GET request by url \"/api/v1/movie/\"{}", movieId);
        return movieService.findById(movieId, currency);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void add(@RequestBody SaveMovieRequest newMovie) {
        log.debug("POST request by url \"/api/v1/movie/\"");
        movieService.add(newMovie);
    }

    @PutMapping(value = "/{movieId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void edit(@PathVariable Long movieId, @RequestBody SaveMovieRequest updatedMovie) {
        movieService.edit(movieId, updatedMovie);
    }


    /**
     * Method assumes that only one parameter received from controller will
     * be initiated with value and the other one will be NULL.
     *
     * @return SortingOrder object with parsed and initiated parameter fields.
     * If both received parameters have NULL values or both rating and price
     * parameters have sorting values, no filter would be applied
     * and SortingOrder.NULL will be returned;
     */
    private SortingOrder validateSortingParams(SortingOrder rating, SortingOrder price) {
        log.debug("Parsing sorting params");
        if (rating != null && price != null) {
            log.debug("Both sorting values are initiated. No filter will be applied as sorting order");
            return SortingOrder.NULL;
        }
        if (rating != null) {
            rating.setParameterName("rating");
            log.debug("Parsed movies order by rating " + rating.getOrder());
            return rating;
        } else if (price != null) {
            price.setParameterName("price");
            log.debug("Parsed movies order by price " + price.getOrder());
            return price;
        }
        return SortingOrder.NULL;
    }
}
