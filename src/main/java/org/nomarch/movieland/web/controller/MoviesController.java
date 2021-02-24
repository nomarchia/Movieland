package org.nomarch.movieland.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.common.CurrencyCode;
import org.nomarch.movieland.common.SortingOrder;
import org.nomarch.movieland.common.SortingParameter;
import org.nomarch.movieland.dto.FullMovieDto;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.entity.UserRole;
import org.nomarch.movieland.mapper.MovieDtoMapper;
import org.nomarch.movieland.request.GetMovieRequest;
import org.nomarch.movieland.request.SaveMovieRequest;
import org.nomarch.movieland.service.MovieService;
import org.nomarch.movieland.web.Secured;
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
    private final MovieDtoMapper movieDtoMapper;

    @Secured(UserRole.GUEST)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> getAll(@RequestParam(name = "rating", required = false) SortingOrder ratingOrder,
                              @RequestParam(name = "price", required = false) SortingOrder priceOrder) {
        GetMovieRequest movieRequest = createMovieRequest(ratingOrder, priceOrder);
        return movieService.findAll(movieRequest);
    }

    @Secured(UserRole.GUEST)
    @GetMapping(value = "/random", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> getRandom() {
        return movieService.findRandom();
    }

    @Secured(UserRole.GUEST)
    @GetMapping(value = "/genre/{genreId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> getByGenre(@PathVariable Integer genreId,
                                  @RequestParam(name = "rating", required = false) SortingOrder ratingOrder,
                                  @RequestParam(name = "price", required = false) SortingOrder priceOrder) {
        log.debug("GET movies by genre id: {}", genreId);
        GetMovieRequest movieRequest = createMovieRequest(ratingOrder, priceOrder);

        return movieService.findByGenre(genreId, movieRequest);
    }

    @GetMapping(value = "/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured(UserRole.GUEST)
    public FullMovieDto getById(@PathVariable Long movieId, @RequestParam(required = false) CurrencyCode currencyCode) {
        return movieService.findById(movieId, currencyCode);
    }

    @Secured(UserRole.ADMIN)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody SaveMovieRequest newMovie) {
        Movie movie = movieDtoMapper.dtoToMovie(newMovie);

        movieService.add(movie);
    }

    @Secured(UserRole.ADMIN)
    @PutMapping(value = "/{movieId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void edit(@PathVariable Long movieId, @RequestBody SaveMovieRequest updatedMovie) {
        Movie movie = movieDtoMapper.dtoToMovie(updatedMovie);
        movie.setId(movieId);
        movieService.edit(movie);
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
