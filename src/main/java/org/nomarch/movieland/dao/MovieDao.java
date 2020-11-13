package org.nomarch.movieland.dao;

import org.nomarch.movieland.entity.Movie;

import java.util.List;

public interface MovieDao {
    List<Movie> getAll();
}
