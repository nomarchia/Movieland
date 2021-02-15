package org.nomarch.movieland.web.controller;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.nomarch.movieland.entity.Movie;
import org.nomarch.movieland.request.GetMovieRequest;
import org.nomarch.movieland.service.MovieService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.TestInstance.Lifecycle.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestInstance(PER_METHOD)
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

        expectedMovies = createExpectedMovies();
    }

    @DisplayName("Get all movies by get(/api/v1/movie) request")
    @Test
    void testGetAll() throws Exception {
        //prepare
        when(movieService.findAll(any(GetMovieRequest.class))).thenReturn(expectedMovies);

        //when
        mockMvc.perform(get("/movie"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.size()").value(3))

                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nameNative").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].nameRussian").value("Побег из Шоушенка"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(1994))
                .andExpect(jsonPath("$[0].rating").value(8.9))
                .andExpect(jsonPath("$[0].price").value(123.45))
                .andExpect(jsonPath("$[0].picturePath").value("https://site.com/img1.jpg"))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nameNative").value("The Green Mile"))
                .andExpect(jsonPath("$[1].nameRussian").value("Зеленая миля"))
                .andExpect(jsonPath("$[1].yearOfRelease").value(1999))
                .andExpect(jsonPath("$[1].rating").value(8.9))
                .andExpect(jsonPath("$[1].price").value(134.67))
                .andExpect(jsonPath("$[1].picturePath").value("https://site.com/img2.jpg"))

                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].nameNative").value("Forrest Gump"))
                .andExpect(jsonPath("$[2].nameRussian").value("Форрест Гамп"))
                .andExpect(jsonPath("$[2].yearOfRelease").value(1994))
                .andExpect(jsonPath("$[2].rating").value(8.6))
                .andExpect(jsonPath("$[2].price").value(200.60))
                .andExpect(jsonPath("$[2].picturePath").value("https://site.com/img3.jpg"));
    }

    @DisplayName("Get all movies by get(/api/v1/movie?price=DESC) request")
    @Test
    void testGetAllWithPriceSortingParam() throws Exception {
        //prepare
        when(movieService.findAll(any(GetMovieRequest.class))).thenReturn(expectedMovies);

        //when
        mockMvc.perform(get("/movie?price=DESC"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.size()").value(3))

                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nameNative").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].nameRussian").value("Побег из Шоушенка"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(1994))
                .andExpect(jsonPath("$[0].rating").value(8.9))
                .andExpect(jsonPath("$[0].price").value(123.45))
                .andExpect(jsonPath("$[0].picturePath").value("https://site.com/img1.jpg"))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nameNative").value("The Green Mile"))
                .andExpect(jsonPath("$[1].nameRussian").value("Зеленая миля"))
                .andExpect(jsonPath("$[1].yearOfRelease").value(1999))
                .andExpect(jsonPath("$[1].rating").value(8.9))
                .andExpect(jsonPath("$[1].price").value(134.67))
                .andExpect(jsonPath("$[1].picturePath").value("https://site.com/img2.jpg"))

                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].nameNative").value("Forrest Gump"))
                .andExpect(jsonPath("$[2].nameRussian").value("Форрест Гамп"))
                .andExpect(jsonPath("$[2].yearOfRelease").value(1994))
                .andExpect(jsonPath("$[2].rating").value(8.6))
                .andExpect(jsonPath("$[2].price").value(200.60))
                .andExpect(jsonPath("$[2].picturePath").value("https://site.com/img3.jpg"));
    }

    @DisplayName("Get three random movies by get(/api/v1/movie/random) request")
    @Test
    void testGetThreeRandom() throws Exception {
        //prepare
        when(movieService.findRandom()).thenReturn(expectedMovies);

        //when
        mockMvc.perform(get("/movie/random"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.size()").value(3))

                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nameNative").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].nameRussian").value("Побег из Шоушенка"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(1994))
                .andExpect(jsonPath("$[0].rating").value(8.9))
                .andExpect(jsonPath("$[0].price").value(123.45))
                .andExpect(jsonPath("$[0].picturePath").value("https://site.com/img1.jpg"))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nameNative").value("The Green Mile"))
                .andExpect(jsonPath("$[1].nameRussian").value("Зеленая миля"))
                .andExpect(jsonPath("$[1].yearOfRelease").value(1999))
                .andExpect(jsonPath("$[1].rating").value(8.9))
                .andExpect(jsonPath("$[1].price").value(134.67))
                .andExpect(jsonPath("$[1].picturePath").value("https://site.com/img2.jpg"))

                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].nameNative").value("Forrest Gump"))
                .andExpect(jsonPath("$[2].nameRussian").value("Форрест Гамп"))
                .andExpect(jsonPath("$[2].yearOfRelease").value(1994))
                .andExpect(jsonPath("$[2].rating").value(8.6))
                .andExpect(jsonPath("$[2].price").value(200.60))
                .andExpect(jsonPath("$[2].picturePath").value("https://site.com/img3.jpg"));
    }

    @DisplayName("Get Movies by Genre by get(/api/v1/movie/genre/{genreId}) request")
    @Test
    void testGetMoviesByGenre() throws Exception {
        //prepare
        when(movieService.findByGenre(eq(1), any(GetMovieRequest.class))).thenReturn(expectedMovies);

        //when
        mockMvc.perform(get("/movie/genre/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.size()").value(3))

                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nameNative").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].nameRussian").value("Побег из Шоушенка"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(1994))
                .andExpect(jsonPath("$[0].rating").value(8.9))
                .andExpect(jsonPath("$[0].price").value(123.45))
                .andExpect(jsonPath("$[0].picturePath").value("https://site.com/img1.jpg"))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nameNative").value("The Green Mile"))
                .andExpect(jsonPath("$[1].nameRussian").value("Зеленая миля"))
                .andExpect(jsonPath("$[1].yearOfRelease").value(1999))
                .andExpect(jsonPath("$[1].rating").value(8.9))
                .andExpect(jsonPath("$[1].price").value(134.67))
                .andExpect(jsonPath("$[1].picturePath").value("https://site.com/img2.jpg"))

                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].nameNative").value("Forrest Gump"))
                .andExpect(jsonPath("$[2].nameRussian").value("Форрест Гамп"))
                .andExpect(jsonPath("$[2].yearOfRelease").value(1994))
                .andExpect(jsonPath("$[2].rating").value(8.6))
                .andExpect(jsonPath("$[2].price").value(200.60))
                .andExpect(jsonPath("$[2].picturePath").value("https://site.com/img3.jpg"));
    }

    @DisplayName("Get Movies by Genre by get(/api/v1/movie/genre/1?rating=ASC) request")
    @Test
    void testGetMoviesByGenreWithRatingSortingParam() throws Exception {
        //prepare
        when(movieService.findByGenre(eq(1), any(GetMovieRequest.class))).thenReturn(expectedMovies);

        //when
        mockMvc.perform(get("/movie/genre/1?rating=ASC"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
//                .andExpect(jsonPath("$.size()").value(3))

                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nameNative").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].nameRussian").value("Побег из Шоушенка"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(1994))
                .andExpect(jsonPath("$[0].rating").value(8.9))
                .andExpect(jsonPath("$[0].price").value(123.45))
                .andExpect(jsonPath("$[0].picturePath").value("https://site.com/img1.jpg"))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nameNative").value("The Green Mile"))
                .andExpect(jsonPath("$[1].nameRussian").value("Зеленая миля"))
                .andExpect(jsonPath("$[1].yearOfRelease").value(1999))
                .andExpect(jsonPath("$[1].rating").value(8.9))
                .andExpect(jsonPath("$[1].price").value(134.67))
                .andExpect(jsonPath("$[1].picturePath").value("https://site.com/img2.jpg"))

                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].nameNative").value("Forrest Gump"))
                .andExpect(jsonPath("$[2].nameRussian").value("Форрест Гамп"))
                .andExpect(jsonPath("$[2].yearOfRelease").value(1994))
                .andExpect(jsonPath("$[2].rating").value(8.6))
                .andExpect(jsonPath("$[2].price").value(200.60))
                .andExpect(jsonPath("$[2].picturePath").value("https://site.com/img3.jpg"));
    }

    private List<Movie> createExpectedMovies() {
        expectedMovies = new ArrayList<>();
        expectedMovies.add(Movie.builder().id(1L).nameNative("The Shawshank Redemption").nameRussian("Побег из Шоушенка")
                .yearOfRelease(1994).rating(8.9).price(123.45).picturePath("https://site.com/img1.jpg").build());
        expectedMovies.add(Movie.builder().id(2L).nameNative("The Green Mile").nameRussian("Зеленая миля")
                .yearOfRelease(1999).rating(8.9).price(134.67).picturePath("https://site.com/img2.jpg").build());
        expectedMovies.add(Movie.builder().id(3L).nameNative("Forrest Gump").nameRussian("Форрест Гамп")
                .yearOfRelease(1994).rating(8.6).price(200.60).picturePath("https://site.com/img3.jpg").build());

        return expectedMovies;
    }
}