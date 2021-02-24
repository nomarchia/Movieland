package org.nomarch.movieland.service.impl.multithreading;

import lombok.RequiredArgsConstructor;
import org.nomarch.movieland.dto.ReviewDto;
import org.nomarch.movieland.entity.Country;
import org.nomarch.movieland.entity.Genre;
import org.nomarch.movieland.service.CountryService;
import org.nomarch.movieland.service.GenreService;
import org.nomarch.movieland.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class MultithreadingEnrichmentTool {
    private final GenreService genreService;
    private final CountryService countryService;
    private final ReviewService reviewService;

    public Map<String, List<?>> enrichDependantEntities(Long movieId) {
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
