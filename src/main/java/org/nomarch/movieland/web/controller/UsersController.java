package org.nomarch.movieland.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.nomarch.movieland.dto.UserUUID;
import org.nomarch.movieland.security.impl.SecurityTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsersController {
    @Autowired
    private SecurityTokenService securityTokenService;

    @PostMapping(value = "login")
    public UserUUID login(@RequestParam String email, @RequestParam String password) {
        return securityTokenService.login(email, password);
    }

    @DeleteMapping(value = "logout")
    public void logout(@RequestHeader String uuid) {
        securityTokenService.logout(uuid);
    }
}
