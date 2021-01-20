package org.nomarch.movieland.dao;

import org.nomarch.movieland.entity.Country;

import java.util.List;

public interface CountryDao {
    List<Country> findAll();
}
