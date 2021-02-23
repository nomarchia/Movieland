package org.nomarch.movieland.service.impl;

import lombok.RequiredArgsConstructor;
import org.nomarch.movieland.common.CurrencyCode;
import org.nomarch.movieland.dao.MovieDao;
import org.nomarch.movieland.dto.FullMovieDto;
import org.nomarch.movieland.dto.ReviewDto;
import org.nomarch.movieland.entity.Country;
import org.nomarch.movieland.entity.Genre;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.mapper.MovieDtoMapper;
import org.nomarch.movieland.request.GetMovieRequest;
import org.nomarch.movieland.service.*;
import org.nomarch.movieland.service.impl.multithreading.GetCountriesCallable;
import org.nomarch.movieland.service.impl.multithreading.GetGenresCallable;
import org.nomarch.movieland.service.impl.multithreading.GetReviewsCallable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class DefaultMovieService implements MovieService {

    private final MovieDao movieDao;
    private final CurrencyService currencyService;
    private final GenreService genreService;
    private final CountryService countryService;
    private final ReviewService reviewService;
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
        Movie movie = movieDao.findById(movieId);

        Double convertedPrice = currencyService.convert(CurrencyCode.UAH, currencyCode, movie.getPrice());
        movie.setPrice(convertedPrice);

        FullMovieDto movieDTO = movieDtoMapper.movieToDto(movie);

        Map<String, List<?>> dependantEntities = enrichDependantEntities(movieId);

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
        movieDao.edit(updatedMovie);
    }

    private Map<String, List<?>> enrichDependantEntities(Long movieId) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<List<Genre>> futureGenres = executorService.submit(new GetGenresCallable(movieId, genreService));
        Future<List<Country>> futureCountries = executorService.submit(new GetCountriesCallable(movieId, countryService));
        Future<List<ReviewDto>> futureReviews = executorService.submit(new GetReviewsCallable(movieId, reviewService));

        Map<String, List<?>> entities = new HashMap<>();
        try {
            List<Genre> genres = futureGenres.get(5, TimeUnit.SECONDS);
            entities.put("genres", genres);
            List<Country> countries = futureCountries.get(5, TimeUnit.SECONDS);
            entities.put("countries", countries);
            List<ReviewDto> reviewDtoList = futureReviews.get(5, TimeUnit.SECONDS);
            entities.put("reviews", reviewDtoList);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.getCause();
        } finally {
            executorService.shutdownNow();
        }

        return entities;
    }
}
