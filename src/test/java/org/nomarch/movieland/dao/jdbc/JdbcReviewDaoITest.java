package org.nomarch.movieland.dao.jdbc;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.nomarch.movieland.RootApplicationContext;
import org.nomarch.movieland.TestContext;
import org.nomarch.movieland.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

@DBRider()
@DBUnit(caseSensitiveTableNames = false, caseInsensitiveStrategy = Orthography.LOWERCASE)
@SpringJUnitWebConfig(value = {TestContext.class, RootApplicationContext.class})
@DataSet(value = {"movies.xml", "users.xml", "reviews.xml"}, cleanAfter = true)
class JdbcReviewDaoITest {
    @Autowired
    JdbcReviewDao jdbcReviewDao;

    @DisplayName("Insert new review into the DB")
    @Test
    @ExpectedDataSet(value = "expected_reviews.xml")
    void testSaveReview() {
        //prepare
        Review review = Review.builder().movieId(4).userId(1).text("Not bad but not good").build();

        //when
        jdbcReviewDao.save(review);
    }
}