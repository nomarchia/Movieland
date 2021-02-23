package org.nomarch.movieland.service.impl.multithreading;

import lombok.RequiredArgsConstructor;
import org.nomarch.movieland.entity.Country;
import org.nomarch.movieland.service.CountryService;

import java.util.List;
import java.util.concurrent.Callable;

@RequiredArgsConstructor
public class GetCountriesCallable implements Callable<List<Country>> {
    private final Long movieId;
    private final CountryService countryService;

    @Override
    public List<Country> call() throws Exception {
        return countryService.findByMovieId(movieId);
    }
}
