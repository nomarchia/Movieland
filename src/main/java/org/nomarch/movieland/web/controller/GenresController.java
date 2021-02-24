package org.nomarch.movieland.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.entity.Genre;
import org.nomarch.movieland.entity.UserRole;
import org.nomarch.movieland.service.GenreService;
import org.nomarch.movieland.web.interceptor.Secured;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/genre", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GenresController {
    private final GenreService genreService;

    @Secured(UserRole.GUEST)
    @GetMapping
    public List<Genre> getAll() {
        return genreService.findAll();
    }
}
