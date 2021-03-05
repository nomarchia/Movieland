package org.nomarch.movieland.service.impl.multithreading;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.dto.ReturnedEntity;
import org.nomarch.movieland.exception.EnrichmentException;
import org.nomarch.movieland.service.CountryService;
import org.nomarch.movieland.service.GenreService;
import org.nomarch.movieland.service.MovieEnrichmentService;
import org.nomarch.movieland.service.ReviewService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class ParallelMovieEnrichmentService<T> implements MovieEnrichmentService {
    private final GenreService genreService;
    private final CountryService countryService;
    private final ReviewService reviewService;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Map<String, List<ReturnedEntity>> enrichDependantEntities(Long movieId) {
        List<Callable<List>> callables = new ArrayList<>();
        callables.add(() -> genreService.findByMovieId(movieId));
        callables.add(() -> countryService.findByMovieId(movieId));
        callables.add(() -> reviewService.findByMovieId(movieId));

        List<Future<List>> futures;
        Map<String, List<ReturnedEntity>> entities = new HashMap<>();
        try {
            futures = executorService.invokeAll(callables, 5, TimeUnit.SECONDS);
            List<ReturnedEntity> genres = futures.get(0).get();
            entities.put("genres", genres);
            List<ReturnedEntity> countries = futures.get(1).get();
            entities.put("countries", countries);
            List<ReturnedEntity> reviewDtoList = futures.get(2).get();
            entities.put("reviews", reviewDtoList);
        } catch (InterruptedException | ExecutionException e) {
            log.debug("Error happened during the parallel enrichment for movie with {}", movieId);
            throw new EnrichmentException(e.getMessage());
        }

        return entities;
    }
}
