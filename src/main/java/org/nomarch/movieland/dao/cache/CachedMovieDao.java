package org.nomarch.movieland.dao.cache;

import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.dao.MovieDao;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.request.GetMovieRequest;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
@Primary
public class CachedMovieDao implements MovieDao {
    private final MovieDao movieDao;
    private final Map<Long, SoftReference<Movie>> idToSoftRefMovie = new ConcurrentHashMap<>();

    public CachedMovieDao(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public List<Movie> findAll(GetMovieRequest getMovieRequest) {
        return movieDao.findAll(getMovieRequest);
    }

    @Override
    public List<Movie> findRandom(Integer moviesAmount) {
        return movieDao.findRandom(moviesAmount);
    }

    @Override
    public List<Movie> findByGenre(Integer genreId, GetMovieRequest getMovieRequest) {
        return movieDao.findByGenre(genreId, getMovieRequest);
    }

    public Movie findById(Long movieId) {
        SoftReference<Movie> movieSoftReference = idToSoftRefMovie.get(movieId);

        if (movieSoftReference == null || movieSoftReference.get() == null) {
            Movie movie = movieDao.findById(movieId);
            movieSoftReference = new SoftReference<>(movie);
            idToSoftRefMovie.put(movieId, movieSoftReference);
        }

        return movieSoftReference.get();
    }

    @Override
    public Long addAndReturnId(Movie newMovie) {
        return movieDao.addAndReturnId(newMovie);
    }

    @Override
    public void add(Movie newMovie) {
        Long movieId = addAndReturnId(newMovie);
        newMovie.setId(movieId);

        idToSoftRefMovie.put(movieId, new SoftReference<>(newMovie));
    }

    public void edit(Movie updatedMovie) {
        movieDao.edit(updatedMovie);

        if (idToSoftRefMovie.containsKey(updatedMovie.getId())) {
            idToSoftRefMovie.put(updatedMovie.getId(), new SoftReference<>(updatedMovie));
        }
    }

}
