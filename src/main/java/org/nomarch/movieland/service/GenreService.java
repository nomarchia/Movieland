package org.nomarch.movieland.service;

import org.nomarch.movieland.entity.Genre;

import java.util.List;

public interface GenreService {
    public List<Genre> getAllGenres();

    public void updateGenresCache();
}