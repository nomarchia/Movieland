package org.nomarch.movieland.dao.jdbc;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.nomarch.movieland.RootApplicationContext;
import org.nomarch.movieland.TestContext;
import org.nomarch.movieland.dao.CountryDao;
import org.nomarch.movieland.entity.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DBRider
@DBUnit(caseSensitiveTableNames = false, caseInsensitiveStrategy = Orthography.LOWERCASE)
@SpringJUnitWebConfig(value = {TestContext.class, RootApplicationContext.class})
class JdbcCountryDaoITest {
    @Autowired
    private CountryDao countryDao;

    @DisplayName("Get all countries from DB")
    @Test
    @DataSet(value = "countries.xml", cleanBefore = true)
    void testFindAll() {
        //prepare
        Country expectedCountryOne = Country.builder().id(1L).name("США").build();
        Country expectedCountryTwo = Country.builder().id(2L).name("Великобритания").build();
        Country expectedCountryThree = Country.builder().id(3L).name("Италия").build();

        //when
        List<Country> countryList = countryDao.findAll();

        //then
        assertNotNull(countryList);
        assertTrue(new ReflectionEquals(expectedCountryOne).matches(countryList.get(0)));
        assertTrue(new ReflectionEquals(expectedCountryTwo).matches(countryList.get(1)));
        assertTrue(new ReflectionEquals(expectedCountryThree).matches(countryList.get(2)));
    }

    @DisplayName("Get all countries by movie id")
    @Test
    @DataSet(value = "movies_genres_countries_and_many_to_many_tables.xml", cleanBefore = true)
    void testGetCountriesByMovieId() {
        //prepare
        Country expectedCountryOne = Country.builder().id(1L).name("США").build();
        Country expectedCountryTwo = Country.builder().id(3L).name("Италия").build();

        //when
        List<Country> actualCountries = countryDao.findByMovieId(6L);

        //then
        assertNotNull(actualCountries);
        assertEquals(2, actualCountries.size());
        assertTrue(new ReflectionEquals(expectedCountryOne).matches(actualCountries.get(0)));
        assertTrue(new ReflectionEquals(expectedCountryTwo).matches(actualCountries.get(1)));
    }
}