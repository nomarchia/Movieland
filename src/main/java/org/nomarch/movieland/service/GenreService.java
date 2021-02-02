package org.nomarch.movieland.service;

import org.nomarch.movieland.entity.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findAll();

    List<Genre> findByMovieId(Long movieId);
}