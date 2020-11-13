package org.nomarch.movieland.dao;

import org.nomarch.movieland.entity.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> getAll();
}
