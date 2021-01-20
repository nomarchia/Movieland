package org.nomarch.movieland.service.impl;

import org.nomarch.movieland.dao.jdbc.JdbcCountryDao;
import org.nomarch.movieland.entity.Country;
import org.nomarch.movieland.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultCountryService implements CountryService {
    @Autowired
    private JdbcCountryDao jdbcCountryDao;

    @Override
    public List<Country> findAll() {
        return jdbcCountryDao.findAll();
    }
}
