package org.nomarch.movieland.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.service.MovieService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


class MoviesControllerTest {
    @Mock
    private MovieService movieService;

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
        when(movieService.getAll()).thenReturn(expectedMovies);

        //when
        mockMvc.perform(get("/v1/movie"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))

                .andExpect(jsonPath("$.size()").value(3))

                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Побег из Шоушенка/The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].country").value("США"))
                .andExpect(jsonPath("$[0].year").value(1994))
                .andExpect(jsonPath("$[0].description").value("Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника."))
                .andExpect(jsonPath("$[0].rating").value(8.9))
                .andExpect(jsonPath("$[0].price").value(123.45))
                .andExpect(jsonPath("$[0].posterImg").value("https://site.com/img1.jpg"))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Зеленая миля/The Green Mile"))
                .andExpect(jsonPath("$[1].country").value("США"))
                .andExpect(jsonPath("$[1].year").value(1999))
                .andExpect(jsonPath("$[1].description").value("Обвиненный в страшном преступлении, Джон Коффи оказывается в блоке смертников тюрьмы «Холодная гора»."))
                .andExpect(jsonPath("$[1].rating").value(8.9))
                .andExpect(jsonPath("$[1].price").value(134.67))
                .andExpect(jsonPath("$[1].posterImg").value("https://site.com/img2.jpg"))

                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].name").value("Форрест Гамп/Forrest Gump"))
                .andExpect(jsonPath("$[2].country").value("США"))
                .andExpect(jsonPath("$[2].year").value(1994))
                .andExpect(jsonPath("$[2].description").value("От лица главного героя Форреста Гампа, слабоумного безобидного человека с благородным и открытым сердцем, рассказывается история его необыкновенной жизни."))
                .andExpect(jsonPath("$[2].rating").value(8.6))
                .andExpect(jsonPath("$[2].price").value(200.60))
                .andExpect(jsonPath("$[2].posterImg").value("https://site.com/img3.jpg"));
    }

    @DisplayName("Get three random movies by get(/v1/movie/random) request")
    @Test
    void testGetThreeRandom() throws Exception {
        //prepare
        when(movieService.getThreeRandom()).thenReturn(expectedMovies);

        //when
        mockMvc.perform(get("/v1/movie/random"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))

                .andExpect(jsonPath("$.size()").value(3))

                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Побег из Шоушенка/The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].country").value("США"))
                .andExpect(jsonPath("$[0].year").value(1994))
                .andExpect(jsonPath("$[0].description").value("Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника."))
                .andExpect(jsonPath("$[0].rating").value(8.9))
                .andExpect(jsonPath("$[0].price").value(123.45))
                .andExpect(jsonPath("$[0].posterImg").value("https://site.com/img1.jpg"))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Зеленая миля/The Green Mile"))
                .andExpect(jsonPath("$[1].country").value("США"))
                .andExpect(jsonPath("$[1].year").value(1999))
                .andExpect(jsonPath("$[1].description").value("Обвиненный в страшном преступлении, Джон Коффи оказывается в блоке смертников тюрьмы «Холодная гора»."))
                .andExpect(jsonPath("$[1].rating").value(8.9))
                .andExpect(jsonPath("$[1].price").value(134.67))
                .andExpect(jsonPath("$[1].posterImg").value("https://site.com/img2.jpg"))

                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].name").value("Форрест Гамп/Forrest Gump"))
                .andExpect(jsonPath("$[2].country").value("США"))
                .andExpect(jsonPath("$[2].year").value(1994))
                .andExpect(jsonPath("$[2].description").value("От лица главного героя Форреста Гампа, слабоумного безобидного человека с благородным и открытым сердцем, рассказывается история его необыкновенной жизни."))
                .andExpect(jsonPath("$[2].rating").value(8.6))
                .andExpect(jsonPath("$[2].price").value(200.60))
                .andExpect(jsonPath("$[2].posterImg").value("https://site.com/img3.jpg"));
    }

    @DisplayName("Get Movies by Genre by get(/v1/movie/genre/{genreId}) request")
    @Test
    void testGetMoviesByGenre() throws Exception {
        //prepare
        when(movieService.getMoviesByGenre(1)).thenReturn(expectedMovies);

        //when
        mockMvc.perform(get("/v1/movie/genre/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))

                .andExpect(jsonPath("$.size()").value(3))

                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Побег из Шоушенка/The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].country").value("США"))
                .andExpect(jsonPath("$[0].year").value(1994))
                .andExpect(jsonPath("$[0].description").value("Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника."))
                .andExpect(jsonPath("$[0].rating").value(8.9))
                .andExpect(jsonPath("$[0].price").value(123.45))
                .andExpect(jsonPath("$[0].posterImg").value("https://site.com/img1.jpg"))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Зеленая миля/The Green Mile"))
                .andExpect(jsonPath("$[1].country").value("США"))
                .andExpect(jsonPath("$[1].year").value(1999))
                .andExpect(jsonPath("$[1].description").value("Обвиненный в страшном преступлении, Джон Коффи оказывается в блоке смертников тюрьмы «Холодная гора»."))
                .andExpect(jsonPath("$[1].rating").value(8.9))
                .andExpect(jsonPath("$[1].price").value(134.67))
                .andExpect(jsonPath("$[1].posterImg").value("https://site.com/img2.jpg"))

                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].name").value("Форрест Гамп/Forrest Gump"))
                .andExpect(jsonPath("$[2].country").value("США"))
                .andExpect(jsonPath("$[2].year").value(1994))
                .andExpect(jsonPath("$[2].description").value("От лица главного героя Форреста Гампа, слабоумного безобидного человека с благородным и открытым сердцем, рассказывается история его необыкновенной жизни."))
                .andExpect(jsonPath("$[2].rating").value(8.6))
                .andExpect(jsonPath("$[2].price").value(200.60))
                .andExpect(jsonPath("$[2].posterImg").value("https://site.com/img3.jpg"));
    }

    private void enrichExpectedMovies() {
        expectedMovies = new ArrayList<>();
        expectedMovies.add(Movie.builder().id(1).name("Побег из Шоушенка/The Shawshank Redemption").country("США").year(1994)
                .description("Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника.")
                .rating(8.9).price(123.45).posterImg("https://site.com/img1.jpg").build());
        expectedMovies.add(Movie.builder().id(2).name("Зеленая миля/The Green Mile").country("США").year(1999)
                .description("Обвиненный в страшном преступлении, Джон Коффи оказывается в блоке смертников тюрьмы «Холодная гора».")
                .rating(8.9).price(134.67).posterImg("https://site.com/img2.jpg").build());
        expectedMovies.add(Movie.builder().id(3).name("Форрест Гамп/Forrest Gump").country("США").year(1994)
                .description("От лица главного героя Форреста Гампа, слабоумного безобидного человека с благородным и открытым сердцем, рассказывается история его необыкновенной жизни.")
                .rating(8.6).price(200.60).posterImg("https://site.com/img3.jpg").build());
    }
}