package org.nomarch.movieland.dao.jdbc;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.nomarch.movieland.RootApplicationContext;
import org.nomarch.movieland.TestContext;
import org.nomarch.movieland.dao.ReviewDao;
import org.nomarch.movieland.entity.Review;
import org.nomarch.movieland.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@DBRider()
@DBUnit(caseSensitiveTableNames = false, caseInsensitiveStrategy = Orthography.LOWERCASE)
@SpringJUnitWebConfig(value = {TestContext.class, RootApplicationContext.class})
class JdbcReviewDaoITest {
    @Autowired
    private ReviewDao reviewDao;

    @DisplayName("Insert new review into the DB")
    @Test
    @DataSet(value = "movies_users_reviews.xml", cleanAfter = true, cleanBefore = true, skipCleaningFor = {"genres"})
    @ExpectedDataSet(value = "expected_reviews.xml")
    void testSaveReview() {
        //prepare
        User user = User.builder().id(1L).build();
        Review review = Review.builder().movieId(4L).user(user).text("Not bad but not good").build();

        //when
        reviewDao.save(review);
    }

    @DisplayName("Get all reviews by movie id")
    @Test
    @DataSet(value = "movies_users_reviews.xml", cleanAfter = true, cleanBefore = true, skipCleaningFor = {"genres"})
    void testFindByMovieId() {
        //prepare
        Review expectedReview = Review.builder().id(2L).text("Eastern european art-house crap...").build();

        //when
        List<Review> actualReviews = reviewDao.findByMovieId(2L);

        //then
        assertEquals(1, actualReviews.size());
        assertTrue(new ReflectionEquals(expectedReview).matches(actualReviews.get(0)));
    }
}