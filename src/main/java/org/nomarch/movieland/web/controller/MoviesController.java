package org.nomarch.movieland.web.controller;

import lombok.RequiredArgsConstructor;
import org.nomarch.movieland.common.Currency;
import org.nomarch.movieland.common.SortingOrder;
import org.nomarch.movieland.common.SortingParameter;
import org.nomarch.movieland.dto.FullMovieDto;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.request.GetMovieRequest;
import org.nomarch.movieland.request.SaveMovieRequest;
import org.nomarch.movieland.service.MovieService;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/movie")
@RequiredArgsConstructor
public class MoviesController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(MoviesController.class);
    private final MovieService movieService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> getAll(@RequestParam(name = "rating", required = false) SortingOrder ratingOrder,
                              @RequestParam(name = "price", required = false) SortingOrder priceOrder) {
        log.debug("GET request by url \"/api/v1/movie\"");
        GetMovieRequest movieRequest = createMovieRequest(ratingOrder, priceOrder);
        return movieService.findAll(movieRequest);
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
        GetMovieRequest movieRequest = createMovieRequest(ratingOrder, priceOrder);

        return movieService.findByGenre(genreId, movieRequest);
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
    private GetMovieRequest createMovieRequest(SortingOrder ratingOrder, SortingOrder priceOrder) {
        log.debug("Parsing sorting params");
        if (ratingOrder != null && priceOrder != null) {
            log.debug("Both sorting values are initiated. No filter will be applied as sorting order");
            return new GetMovieRequest();
        }
        if (ratingOrder != null) {
            log.debug("Parsed movies order by rating " + ratingOrder.getOrderDirection());
            return GetMovieRequest.builder().sortingParameter(SortingParameter.RATING).sortingOrder(ratingOrder).build();
        } else if (priceOrder != null) {
            log.debug("Parsed movies order by price " + priceOrder.getOrderDirection());
            return GetMovieRequest.builder().sortingParameter(SortingParameter.PRICE).sortingOrder(priceOrder).build();
        }
        return new GetMovieRequest();
    }
}
