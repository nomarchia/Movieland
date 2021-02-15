package org.nomarch.movieland.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.dao.GenreDao;
import org.nomarch.movieland.entity.Genre;
import org.nomarch.movieland.service.GenreService;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultGenreService implements GenreService {
    private final GenreDao genreDao;

    @Override
    public List<Genre> findAll() {
        return genreDao.findAll();
    }

    @Override
    public List<Genre> findByMovieId(Long movieId) {
        return genreDao.findByMovieId(movieId);
    }
}
