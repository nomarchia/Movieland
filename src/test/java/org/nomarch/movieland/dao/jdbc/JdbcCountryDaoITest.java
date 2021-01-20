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
    private JdbcCountryDao jdbcCountryDao;

    @DisplayName("Get all countries from DB")
    @Test
    @DataSet(value = "countries.xml")
    void testFindAll() {
        //prepare
        Country expectedCountryOne = Country.builder().id(1).name("Великобритания").build();
        Country expectedCountryTwo = Country.builder().id(2).name("Украина").build();
        Country expectedCountryThree = Country.builder().id(3).name("Италия").build();

        //when
        List<Country> countryList = jdbcCountryDao.findAll();

        //then
        assertNotNull(countryList);
        assertTrue(new ReflectionEquals(expectedCountryOne).matches(countryList.get(0)));
        assertTrue(new ReflectionEquals(expectedCountryTwo).matches(countryList.get(1)));
        assertTrue(new ReflectionEquals(expectedCountryThree).matches(countryList.get(2)));
    }
}