package org.nomarch.movieland.dao.cache;

import lombok.RequiredArgsConstructor;
import org.nomarch.movieland.dao.MovieDao;
import org.nomarch.movieland.entity.Movie;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.WeakHashMap;

@Repository
@RequiredArgsConstructor
public class CachedMovieDao {
    private final MovieDao movieDao;
    private final Map<Long, Movie> idToMovieMap = new WeakHashMap<>();

    public Movie findById(Long movieId) {
        Movie movie = idToMovieMap.get(movieId);

        if (movie == null) {
            movie = movieDao.findById(movieId);
            idToMovieMap.put(movieId, movie);
        }

        return movie;
    }

    public void edit(Movie updatedMovie) {
        movieDao.edit(updatedMovie);

        if (idToMovieMap.containsKey(updatedMovie.getId())) {
            idToMovieMap.put(updatedMovie.getId(), updatedMovie);
        }
    }
}
