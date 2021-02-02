package org.nomarch.movieland.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.common.currency.Currency;
import org.nomarch.movieland.common.sorting.SortingOrder;
import org.nomarch.movieland.common.sorting.util.ParamsUtil;
import org.nomarch.movieland.dto.movie.MovieReceivedDTO;
import org.nomarch.movieland.dto.movie.MovieReturnedDTO;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.entity.User;
import org.nomarch.movieland.entity.UserRole;
import org.nomarch.movieland.exception.InsufficientAccessRightsException;
import org.nomarch.movieland.security.SecurityService;
import org.nomarch.movieland.service.MovieService;
import org.nomarch.movieland.dto.movie.MovieRequest;
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
    private final SecurityService securityTokenService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> getAll(@RequestParam(name = "rating", required = false) SortingOrder ratingOrder,
                              @RequestParam(name = "price", required = false) SortingOrder priceOrder) {
        log.debug("GET request by url \"/api/v1/movie\"");
        MovieRequest movieRequest = ParamsUtil.addSortingParams(ratingOrder, priceOrder);
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
        MovieRequest movieRequest = ParamsUtil.addSortingParams(ratingOrder, priceOrder);

        return movieService.findByGenre(genreId, movieRequest);
    }

    @GetMapping(value = "/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MovieReturnedDTO getById(@PathVariable Long movieId, @RequestParam(required = false) Currency currency) {
        log.debug("GET request by url \"/api/v1/movie/\"{}", movieId);
        return movieService.findById(movieId, currency);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void add(@RequestHeader String uuid, @RequestBody MovieReceivedDTO newMovie) {
        log.debug("POST request by url \"/api/v1/movie/\"");
//        checkAdminRights(uuid);
        movieService.add(newMovie);
    }

    @PutMapping(value = "/{movieId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void edit(@PathVariable Long movieId, @RequestHeader String uuid,
                     @RequestBody MovieReceivedDTO updatedMovie) {
//        checkAdminRights(uuid);
        movieService.edit(movieId, updatedMovie);
    }

    //TODO: I'm not sure if it's okay to use a private method here or would be better to extract it to some util class?
//    private void checkAdminRights(String uuid) {
//        User user = securityTokenService.findUserByUUIDToken(uuid);
//        if (user.getRole() != UserRole.ADMIN) {
//            throw new InsufficientAccessRightsException("User " + user + "doesn't have admin rights");
//        }
//    }
}
