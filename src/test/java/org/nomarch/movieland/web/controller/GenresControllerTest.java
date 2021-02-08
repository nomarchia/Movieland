package org.nomarch.movieland.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.nomarch.movieland.entity.Genre;
import org.nomarch.movieland.service.GenreService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class GenresControllerTest {
    @Mock
    private GenreService genreService;

    @InjectMocks
    private GenresController genresController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(genresController).build();
    }

    @DisplayName("Get all genres by get(/api/v1/genre) request")
    @Test
    void testGetAll() throws Exception {
        //prepare
        List<Genre> expectedGenres = new ArrayList<>();
        expectedGenres.add(Genre.builder().id(1).name("драма").build());
        expectedGenres.add(Genre.builder().id(2).name("криминал").build());
        expectedGenres.add(Genre.builder().id(3).name("фэнтези").build());

        when(genreService.findAll())
                .thenReturn(expectedGenres);

        //when
        mockMvc.perform(get("/genre"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))

                .andExpect(jsonPath("$.size()").value(3))

                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("драма"))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("криминал"))

                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].name").value("фэнтези"));

    }
}