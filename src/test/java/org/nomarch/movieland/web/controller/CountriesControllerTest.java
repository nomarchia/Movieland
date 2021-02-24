package org.nomarch.movieland.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.nomarch.movieland.entity.Country;
import org.nomarch.movieland.service.CountryService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CountriesControllerTest {
    @Mock
    private CountryService countryService;
    @InjectMocks
    private CountriesController countriesController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(countriesController).build();
    }

    @Test
    void testGetAllCountries() throws Exception {
        //prepare
        List<Country> expectedCountries = new ArrayList<>();
        expectedCountries.add(Country.builder().id(1L).name("Country1").build());
        expectedCountries.add(Country.builder().id(2L).name("Country2").build());
        expectedCountries.add(Country.builder().id(3L).name("Country3").build());

        when(countryService.findAll())
                .thenReturn(expectedCountries);

        //when
        mockMvc.perform(get("/country"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.size()").value(3))

                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Country1"))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Country2"))

                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].name").value("Country3"));


    }
}