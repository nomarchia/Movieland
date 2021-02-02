package org.nomarch.movieland.service.impl;

import lombok.RequiredArgsConstructor;
import org.nomarch.movieland.dao.CountryDao;
import org.nomarch.movieland.entity.Country;
import org.nomarch.movieland.service.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultCountryService implements CountryService {
    private final CountryDao countryDao;

    @Override
    public List<Country> findAll() {
        return countryDao.findAll();
    }

    @Override
    public List<Country> findByMovieId(Long movieId) {
        return countryDao.findByMovieId(movieId);
    }
}
