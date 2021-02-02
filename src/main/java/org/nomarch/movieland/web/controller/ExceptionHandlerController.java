package org.nomarch.movieland.web.controller;

import org.nomarch.movieland.exception.IncorrectCredentialsException;
import org.nomarch.movieland.exception.InsufficientAccessRightsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(IncorrectCredentialsException.class)
    public HttpStatus badRequest(IncorrectCredentialsException e) {
        return HttpStatus.BAD_REQUEST;
    }

    @ExceptionHandler(InsufficientAccessRightsException.class)
    public HttpStatus forbidden(InsufficientAccessRightsException e) {
        return HttpStatus.FORBIDDEN;
    }
}
