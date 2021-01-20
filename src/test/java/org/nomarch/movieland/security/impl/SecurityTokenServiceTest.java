package org.nomarch.movieland.security.impl;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.nomarch.movieland.RootApplicationContext;
import org.nomarch.movieland.TestContext;
import org.nomarch.movieland.dto.UserUUID;
import org.nomarch.movieland.exception.IncorrectCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DBRider
@DBUnit(caseSensitiveTableNames = false, caseInsensitiveStrategy = Orthography.LOWERCASE)
@SpringJUnitWebConfig(value = {TestContext.class, RootApplicationContext.class})
@DataSet(value = "users.xml")
class SecurityTokenServiceTest {
    @Autowired
    SecurityTokenService securityTokenService;

    @DisplayName("Login with correct credentials")
    @Test
    void testLogin() {
        //when
        UserUUID userUUID = securityTokenService.login("ramzes@egyptmail.com", "mummy");

        //then
        assertNotNull(userUUID);
        assertNotNull(userUUID.getUuid());
        assertEquals("Рамзес Второй", userUUID.getNickname());
    }

    @DisplayName("Login with incorrect credentials")
    @Test
    void testLoginWithWrongCredentials() {
        assertThrows(IncorrectCredentialsException.class, () -> securityTokenService.login("wrong@email", "wrongPassword"));
    }

    @DisplayName("Validate uuid token and get user's id by the token")
    @Test
    void testValidateUUID() {
        //prepare
        UserUUID userUUID = securityTokenService.login("ramzes@egyptmail.com", "mummy");
        assertNotNull(userUUID);

        //when
        long userId = securityTokenService.findUserIdByUUIDToken(userUUID.getUuid());

        //then
        assertEquals(1, userId);
    }

    @DisplayName("Clear session from cache when expiry time is reached")
    @Test
    void clearExpiredSessionFromCache() throws InterruptedException {
        //prepare
        log.info("Login and acquire a uuid");
        UserUUID userUUID = securityTokenService.login("ramzes@egyptmail.com", "mummy");
        log.info("Check that new uuid was added to the cache (uuid lifetime is 10 second)");
        assertEquals(1, securityTokenService.findUserIdByUUIDToken(userUUID.getUuid()));

        log.info("Sleep thread for 15 seconds (cache clearing interval is every 10 seconds)");
        Thread.sleep(15000);

        //then
        log.info("Check that entry with current uuid was cleared from the cache");
        assertThrows(IncorrectCredentialsException.class, () -> securityTokenService.findUserIdByUUIDToken(userUUID.getUuid()));
    }
}