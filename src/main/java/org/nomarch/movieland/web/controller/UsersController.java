package org.nomarch.movieland.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.dto.LoginInfo;
import org.nomarch.movieland.security.SecurityService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UsersController {
    private final SecurityService securityService;

    @PostMapping(value = "login")
    public LoginInfo login(@RequestParam String email, @RequestParam String password) {
        log.debug("Login attempt for user with email address: {}", email);
        return securityService.login(email, password);
    }

    @DeleteMapping(value = "logout")
    public void logout(@RequestHeader String uuid) {
        log.debug("Logout attempt for user with uuid token: {}", uuid);
        securityService.logout(uuid);
    }
}
