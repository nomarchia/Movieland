package org.nomarch.movieland.service.impl.multithreading;

import lombok.RequiredArgsConstructor;
import org.nomarch.movieland.entity.Genre;
import org.nomarch.movieland.service.GenreService;

import java.util.List;
import java.util.concurrent.Callable;

@RequiredArgsConstructor
public class GetGenresCallable implements Callable<List<Genre>> {
    private final Long movieId;
    private final GenreService genreService;

    @Override
    public List<Genre> call() throws Exception {
        return genreService.findByMovieId(movieId);
    }
}
