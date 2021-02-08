package org.nomarch.movieland.dao.jdbc;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.nomarch.movieland.RootApplicationContext;
import org.nomarch.movieland.TestContext;
import org.nomarch.movieland.dao.UserDao;
import org.nomarch.movieland.entity.User;
import org.nomarch.movieland.entity.UserRole;
import org.nomarch.movieland.exception.IncorrectCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import static org.junit.jupiter.api.Assertions.*;

@DBRider
@DBUnit(caseSensitiveTableNames = false, caseInsensitiveStrategy = Orthography.LOWERCASE)
@SpringJUnitWebConfig(value = {TestContext.class, RootApplicationContext.class})
class JdbcUserDaoITest {
    @Autowired
    private UserDao userDao;

    @DisplayName("Test login - get a user with its id and full name after checking his email and password")
    @Test
    @DataSet(value = "users_and_user_roles.xml", cleanBefore = true, skipCleaningFor = {"genres"})
    void testLogin() {
        //prepare
        String expectedFullName = "Рамзес Второй";
        //when
        User actualUser = userDao.login("ramzes@egyptmail.com", "mummy");
        //then
        assertEquals(expectedFullName, actualUser.getNickname());
        assertEquals(1, actualUser.getId());
        assertEquals(UserRole.USER, actualUser.getRole());
    }

    @DisplayName("Test login with non-existing value")
    @Test
    @DataSet(value = "users_and_user_roles.xml", cleanBefore = true, skipCleaningFor = {"genres"})
    void testLoginWithNonExistingValue() {
        assertThrows(IncorrectCredentialsException.class, () -> userDao.login("wrong@email", "wrongPassword"));
    }
}