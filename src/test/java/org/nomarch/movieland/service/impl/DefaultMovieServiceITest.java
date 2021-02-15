package org.nomarch.movieland.service.impl;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.nomarch.movieland.MovielandApplicationContext;
import org.nomarch.movieland.TestContext;
import org.nomarch.movieland.common.Currency;
import org.nomarch.movieland.dto.FullMovieDto;
import org.nomarch.movieland.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import static org.junit.jupiter.api.Assertions.*;


@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@SpringJUnitWebConfig(value = {TestContext.class, MovielandApplicationContext.class})
class DefaultMovieServiceITest {
    @Autowired
    private MovieService movieService;

    @DisplayName("Get reviews movie by id")
    @Test
    @DataSet(value = "movies_genres_countries_and_reviews.xml", cleanBefore = true, skipCleaningFor = {"genres"})
    void testFindById() {
        //prepare

        //when
        FullMovieDto returnedDTO = movieService.findById(2L, Currency.USD);

        //then
        assertNotNull(returnedDTO);
        assertEquals("The Shawshank Redemption", returnedDTO.getNameNative());
        assertEquals("Побег из Шоушенка", returnedDTO.getNameRussian());
        assertEquals(1994, returnedDTO.getYearOfRelease());
        assertEquals("a", returnedDTO.getDescription());
        assertTrue(returnedDTO.getPrice() > 0);
        assertEquals("image1.jpg", returnedDTO.getPicturePath());

        assertEquals(1, returnedDTO.getGenreList().size());
        assertEquals("драма", returnedDTO.getGenreList().get(0).getName());

        assertEquals(1, returnedDTO.getCountryList().size());
        assertEquals("США", returnedDTO.getCountryList().get(0).getName());

        assertEquals(1, returnedDTO.getReviewList().size());
        assertEquals("Good movie!", returnedDTO.getReviewList().get(0).getText());
    }
}