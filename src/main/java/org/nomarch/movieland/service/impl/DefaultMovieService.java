package org.nomarch.movieland.service.impl;

import lombok.RequiredArgsConstructor;
import org.nomarch.movieland.common.CurrencyCode;
import org.nomarch.movieland.dao.MovieDao;
import org.nomarch.movieland.dao.cache.CachedMovieDao;
import org.nomarch.movieland.dto.FullMovieDto;
import org.nomarch.movieland.dto.ReviewDto;
import org.nomarch.movieland.entity.Country;
import org.nomarch.movieland.entity.Genre;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.mapper.MovieDtoMapper;
import org.nomarch.movieland.request.GetMovieRequest;
import org.nomarch.movieland.service.*;
import org.nomarch.movieland.service.impl.multithreading.MultithreadingEnrichmentTool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DefaultMovieService implements MovieService {

    private final CachedMovieDao cachedMovieDao;
    private final MovieDao movieDao;
    private final CurrencyService currencyService;
    private final MultithreadingEnrichmentTool multithreadingEnrichmentTool;
    private final MovieDtoMapper movieDtoMapper;
    @Value("${random.movies.amount:3}")
    private Integer moviesAmount;

    @Override
    public List<Movie> findAll(GetMovieRequest getMovieRequest) {
        return movieDao.findAll(getMovieRequest);
    }

    @Override
    public List<Movie> findRandom(){
        return movieDao.findRandom(moviesAmount);
    }

    @Override
    public List<Movie> findByGenre(Integer genreId, GetMovieRequest getMovieRequest) {
        return movieDao.findByGenre(genreId, getMovieRequest);
    }

    @Override
    public FullMovieDto findById(Long movieId, CurrencyCode currencyCode) {
        Movie movie = cachedMovieDao.findById(movieId);

        if (currencyCode != null && movie.getPrice() != null) {
            Double convertedPrice = currencyService.convert(CurrencyCode.UAH, currencyCode, movie.getPrice());
            movie.setPrice(convertedPrice);
        }

        FullMovieDto movieDTO = movieDtoMapper.movieToDto(movie);

        Map<String, List<?>> dependantEntities = multithreadingEnrichmentTool.enrichDependantEntities(movieId);

        movieDTO.setGenreList((List<Genre>) dependantEntities.get("genres"));
        movieDTO.setCountryList((List<Country>) dependantEntities.get("countries"));
        movieDTO.setReviewList((List<ReviewDto>) dependantEntities.get("reviews"));

        return movieDTO;
    }

    @Override
    public void add(Movie newMovie) {
        movieDao.add(newMovie);
    }

    @Override
    public void edit(Movie updatedMovie) {
        cachedMovieDao.edit(updatedMovie);
    }
}
