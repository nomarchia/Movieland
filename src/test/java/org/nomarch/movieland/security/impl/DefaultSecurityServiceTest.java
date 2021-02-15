package org.nomarch.movieland.security.impl;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.nomarch.movieland.MovielandApplicationContext;
import org.nomarch.movieland.TestContext;
import org.nomarch.movieland.dto.LoginInfo;
import org.nomarch.movieland.entity.User;
import org.nomarch.movieland.entity.UserRole;
import org.nomarch.movieland.exception.IncorrectCredentialsException;
import org.nomarch.movieland.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@SpringJUnitWebConfig(value = {TestContext.class, MovielandApplicationContext.class})
class DefaultSecurityServiceTest {
    @Autowired
    SecurityService securityService;

    @DisplayName("Login with correct credentials")
    @Test
    @DataSet(value = "users_and_user_roles.xml", cleanBefore = true, skipCleaningFor = {"genres"})
    void testLogin() {
        //when
        LoginInfo loginInfo = securityService.login("ramzes@egyptmail.com", "mummy");

        //then
        assertNotNull(loginInfo);
        assertNotNull(loginInfo.getUuid());
        assertEquals("Рамзес Второй", loginInfo.getNickname());
    }

    @DisplayName("Login with incorrect credentials")
    @Test
    @DataSet(value = "users_and_user_roles.xml", cleanBefore = true, skipCleaningFor = {"genres"})
    void testLoginWithWrongCredentials() {
        assertThrows(IncorrectCredentialsException.class, () -> securityService.login("wrong@email", "wrongPassword"));
    }

    @DisplayName("Validate uuid token and get a user associated with this token")
    @Test
    @DataSet(value = "users_and_user_roles.xml", cleanBefore = true, skipCleaningFor = {"genres"})
    void testValidateUUID() {
        //prepare
        LoginInfo loginInfo = securityService.login("ramzes@egyptmail.com", "mummy");
        assertNotNull(loginInfo);

        //when
        User user = securityService.findUserByUUIDToken(loginInfo.getUuid());

        //then
        assertEquals(1, user.getId());
        assertEquals(UserRole.USER, user.getRole());
    }

    @DisplayName("Clear session from cache when expiry time is reached")
    @Test
    @DataSet(value = "users_and_user_roles.xml", cleanBefore = true, skipCleaningFor = {"genres"})
    void clearExpiredSessionFromCache() throws InterruptedException {
        //prepare
        User expectedUser = User.builder().id(1L).nickname("Рамзес Второй").role(UserRole.USER).build();
        log.info("Login and acquire a uuid");
        LoginInfo loginInfo = securityService.login("ramzes@egyptmail.com", "mummy");
        log.info("Check that new uuid was added to the cache (uuid lifetime is 10 second)");
        assertTrue(new ReflectionEquals(expectedUser).matches(securityService.findUserByUUIDToken(loginInfo.getUuid())));

        log.info("Sleep thread for 15 seconds (cache clearing interval is every 10 seconds)");
        Thread.sleep(15000);

        //then
        log.info("Check that entry with current uuid was cleared from the cache");
        assertThrows(IncorrectCredentialsException.class, () -> securityService.findUserByUUIDToken(loginInfo.getUuid()));
    }
}