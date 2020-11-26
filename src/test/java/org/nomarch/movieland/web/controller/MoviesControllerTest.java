package org.nomarch.movieland.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.service.impl.DefaultMovieService;
import org.nomarch.movieland.web.util.SortingUtil;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


class MoviesControllerTest {
    @Mock
    private DefaultMovieService movieService;

    @InjectMocks
    private MoviesController moviesController;

    private MockMvc mockMvc;
    private List<Movie> expectedMovies;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(moviesController).build();

        enrichExpectedMovies();
    }

    @DisplayName("Get all movies by get(/v1/movie) request")
    @Test
    void testGetAll() throws Exception {
        //prepare
        when(movieService.getAllMovies(any(SortingUtil.class))).thenReturn(expectedMovies);

        //when
        mockMvc.perform(get("/v1/movie"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))

                .andExpect(jsonPath("$.size()").value(3))

                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nameNative").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].nameRussian").value("Побег из Шоушенка"))
                .andExpect(jsonPath("$[0].country").value("США"))
                .andExpect(jsonPath("$[0].year").value(1994))
                .andExpect(jsonPath("$[0].rating").value(8.9))
                .andExpect(jsonPath("$[0].price").value(123.45))
                .andExpect(jsonPath("$[0].posterImg").value("https://site.com/img1.jpg"))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nameNative").value("The Green Mile"))
                .andExpect(jsonPath("$[1].nameRussian").value("Зеленая миля"))
                .andExpect(jsonPath("$[1].country").value("США"))
                .andExpect(jsonPath("$[1].year").value(1999))
                .andExpect(jsonPath("$[1].rating").value(8.9))
                .andExpect(jsonPath("$[1].price").value(134.67))
                .andExpect(jsonPath("$[1].posterImg").value("https://site.com/img2.jpg"))

                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].nameNative").value("Forrest Gump"))
                .andExpect(jsonPath("$[2].nameRussian").value("Форрест Гамп"))
                .andExpect(jsonPath("$[2].country").value("США"))
                .andExpect(jsonPath("$[2].year").value(1994))
                .andExpect(jsonPath("$[2].rating").value(8.6))
                .andExpect(jsonPath("$[2].price").value(200.60))
                .andExpect(jsonPath("$[2].posterImg").value("https://site.com/img3.jpg"));
    }

    @DisplayName("Get three random movies by get(/v1/movie/random) request")
    @Test
    void testGetThreeRandom() throws Exception {
        //prepare
        when(movieService.getRandomMovies()).thenReturn(expectedMovies);

        //when
        mockMvc.perform(get("/v1/movie/random"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))

                .andExpect(jsonPath("$.size()").value(3))

                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nameNative").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].nameRussian").value("Побег из Шоушенка"))
                .andExpect(jsonPath("$[0].country").value("США"))
                .andExpect(jsonPath("$[0].year").value(1994))
                .andExpect(jsonPath("$[0].rating").value(8.9))
                .andExpect(jsonPath("$[0].price").value(123.45))
                .andExpect(jsonPath("$[0].posterImg").value("https://site.com/img1.jpg"))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nameNative").value("The Green Mile"))
                .andExpect(jsonPath("$[1].nameRussian").value("Зеленая миля"))
                .andExpect(jsonPath("$[1].country").value("США"))
                .andExpect(jsonPath("$[1].year").value(1999))
                .andExpect(jsonPath("$[1].rating").value(8.9))
                .andExpect(jsonPath("$[1].price").value(134.67))
                .andExpect(jsonPath("$[1].posterImg").value("https://site.com/img2.jpg"))

                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].nameNative").value("Forrest Gump"))
                .andExpect(jsonPath("$[2].nameRussian").value("Форрест Гамп"))
                .andExpect(jsonPath("$[2].country").value("США"))
                .andExpect(jsonPath("$[2].year").value(1994))
                .andExpect(jsonPath("$[2].rating").value(8.6))
                .andExpect(jsonPath("$[2].price").value(200.60))
                .andExpect(jsonPath("$[2].posterImg").value("https://site.com/img3.jpg"));
    }

    @DisplayName("Get Movies by Genre by get(/v1/movie/genre/{genreId}) request")
    @Test
    void testGetMoviesByGenre() throws Exception {
        //prepare
        when(movieService.getMoviesByGenre(1, new SortingUtil())).thenReturn(expectedMovies);

        //when
        mockMvc.perform(get("/v1/movie/genre/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))

                .andExpect(jsonPath("$.size()").value(3))

                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nameNative").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].nameRussian").value("Побег из Шоушенка"))
                .andExpect(jsonPath("$[0].country").value("США"))
                .andExpect(jsonPath("$[0].year").value(1994))
                .andExpect(jsonPath("$[0].rating").value(8.9))
                .andExpect(jsonPath("$[0].price").value(123.45))
                .andExpect(jsonPath("$[0].posterImg").value("https://site.com/img1.jpg"))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nameNative").value("The Green Mile"))
                .andExpect(jsonPath("$[1].nameRussian").value("Зеленая миля"))
                .andExpect(jsonPath("$[1].country").value("США"))
                .andExpect(jsonPath("$[1].year").value(1999))
                .andExpect(jsonPath("$[1].rating").value(8.9))
                .andExpect(jsonPath("$[1].price").value(134.67))
                .andExpect(jsonPath("$[1].posterImg").value("https://site.com/img2.jpg"))

                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].nameNative").value("Forrest Gump"))
                .andExpect(jsonPath("$[2].nameRussian").value("Форрест Гамп"))
                .andExpect(jsonPath("$[2].country").value("США"))
                .andExpect(jsonPath("$[2].year").value(1994))
                .andExpect(jsonPath("$[2].rating").value(8.6))
                .andExpect(jsonPath("$[2].price").value(200.60))
                .andExpect(jsonPath("$[2].posterImg").value("https://site.com/img3.jpg"));
    }

    private void enrichExpectedMovies() {
        expectedMovies = new ArrayList<>();
        expectedMovies.add(Movie.builder().id(1).nameNative("The Shawshank Redemption").nameRussian("Побег из Шоушенка").country("США")
                .year(1994).rating(8.9).price(123.45).posterImg("https://site.com/img1.jpg").build());
        expectedMovies.add(Movie.builder().id(2).nameNative("The Green Mile").nameRussian("Зеленая миля").country("США")
                .year(1999).rating(8.9).price(134.67).posterImg("https://site.com/img2.jpg").build());
        expectedMovies.add(Movie.builder().id(3).nameNative("Forrest Gump").nameRussian("Форрест Гамп").country("США")
                .year(1994).rating(8.6).price(200.60).posterImg("https://site.com/img3.jpg").build());
    }
}