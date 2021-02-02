package org.nomarch.movieland.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.common.sorting.SortingOrder;
import org.nomarch.movieland.common.sorting.util.ParamsUtil;
import org.nomarch.movieland.dto.movie.MovieDTO;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.entity.User;
import org.nomarch.movieland.entity.UserRole;
import org.nomarch.movieland.exception.InsufficientAccessRightsException;
import org.nomarch.movieland.security.impl.SecurityTokenService;
import org.nomarch.movieland.service.impl.DefaultMovieService;
import org.nomarch.movieland.dto.movie.MovieRequest;
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
    @Autowired
    private SecurityTokenService securityTokenService;

    @GetMapping
    public List<Movie> getAll(@RequestParam(name = "rating", required = false) SortingOrder ratingOrder,
                              @RequestParam(name = "price", required = false) SortingOrder priceOrder) {
        log.debug("Get request by url \"/api/v1/movie\"");
        MovieRequest movieRequest = ParamsUtil.addSortingParams(ratingOrder, priceOrder);
        return movieService.findAll(movieRequest);
    }

    @GetMapping(value = "/random")
    public List<Movie> getRandom() {
        log.debug("Get request by url \"/api/v1/movie/random\"\"");

        return movieService.findRandom();
    }

    @GetMapping(value = "/genre/{genreId}")
    public List<Movie> getByGenre(@PathVariable Integer genreId,
                                  @RequestParam(name = "rating", required = false) SortingOrder ratingOrder,
                                  @RequestParam(name = "price", required = false) SortingOrder priceOrder) {
        log.debug("Get request by url \"/api/v1/movie/genre/\"" + genreId);
        MovieRequest movieRequest = ParamsUtil.addSortingParams(ratingOrder, priceOrder);

        return movieService.findByGenre(genreId, movieRequest);
    }

    @GetMapping(value = "/{movieId}")
    public Movie getById(@PathVariable Integer movieId, @RequestParam(required = false) String currency) {
        log.debug("Get request by url \"/api/v1/movie/\"" + movieId);
        return movieService.findById(movieId, currency);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void add(@RequestHeader String uuid, @RequestBody MovieDTO newMovie) {
        checkAdminRights(uuid);
        movieService.add(newMovie);
    }

    @PutMapping(value = "/{movieId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void edit(@PathVariable Integer movieId, @RequestHeader String uuid,
                     @RequestBody MovieDTO updatedMovie) {
        checkAdminRights(uuid);
        movieService.edit(movieId, updatedMovie);
    }

    //TODO: I'm not sure if it's okay to use a private method here or would be better to extract it to some util class?
    private void checkAdminRights(String uuid) {
        User user = securityTokenService.findUserByUUIDToken(uuid);
        if (user.getRole() != UserRole.ADMIN) {
            throw new InsufficientAccessRightsException("User " + user + "doesn't have admin rights");
        }
    }
}
