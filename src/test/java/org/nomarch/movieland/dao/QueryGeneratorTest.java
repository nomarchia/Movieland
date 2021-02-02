package org.nomarch.movieland.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.nomarch.movieland.dto.movie.MovieDTO;

import static org.junit.jupiter.api.Assertions.*;

class QueryGeneratorTest {

    @DisplayName("Generate update query for updated movie")
    @Test
    void testFormUpdateQueryForFullyUpdatedMovie() {
        //prepare
        MovieDTO updatedMovie = MovieDTO.builder().nameNative("Changed title").nameRussian("Изменное название")
                .yearOfRelease(2020).description("new description").price(200.1).picturePath("newPoster.jpg").build();
        String expectedQuery = "UPDATE movies SET name_native = 'Changed title', name_russian = 'Изменное название', year = 2020, " +
                "description = 'new description', price = 200.1, poster_img = 'newPoster.jpg' WHERE id = ?";

        //when
        String actualQuery = QueryGenerator.formMovieUpdateQuery(updatedMovie);

        //then
        assertEquals(expectedQuery, actualQuery);
    }

    @DisplayName("Generate update query for partly updated movie")
    @Test
    void testFormUpdateQueryForPartlyUpdatedMovie() {
        //prepare
        MovieDTO updatedMovie = MovieDTO.builder().nameRussian("Изменное название").price(200.1).picturePath("newPoster.jpg").build();
        String expectedQuery = "UPDATE movies SET name_russian = 'Изменное название', price = 200.1, poster_img = 'newPoster.jpg' WHERE id = ?";

        //when
        String actualQuery = QueryGenerator.formMovieUpdateQuery(updatedMovie);

        //then
        assertEquals(expectedQuery, actualQuery);
    }
}