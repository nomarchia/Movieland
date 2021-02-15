package org.nomarch.movieland.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.nomarch.movieland.entity.Review;
import org.nomarch.movieland.entity.User;
import org.nomarch.movieland.entity.UserRole;
import org.nomarch.movieland.mapper.ReviewDtoMapper;
import org.nomarch.movieland.request.ReviewRequest;
import org.nomarch.movieland.security.UserHolder;
import org.nomarch.movieland.service.ReviewService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReviewsControllerTest {
    @Mock
    private ReviewService reviewService;
    @Mock
    private ReviewDtoMapper reviewDtoMapper;
    @InjectMocks
    private ReviewsController reviewsController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        User testUser = User.builder().id(1L).nickname("Salah ad-Din").role(UserRole.USER).build();
        UserHolder.setUser(testUser);

        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reviewsController).build();
    }

    @DisplayName("Test add new review by POST /review request")
    @Test
    void testAddReview() throws Exception {
        //prepare
        String reviewJsonRequestBody = "{\n" +
                "\"movieId\" : 1,\n" +
                "\"text\" : \"Очень понравилось!\"\n" +
                "}";
        when(reviewDtoMapper.dtoToReview(any(ReviewRequest.class))).thenReturn(Review.builder().id(1L).text("Очень понравилось!").build());

        //when
        mockMvc.perform(post("/review")
                .header("uuid", "any-uuid")
                .contentType(MediaType.APPLICATION_JSON).content(reviewJsonRequestBody))
                .andExpect(status().isCreated());
    }
}