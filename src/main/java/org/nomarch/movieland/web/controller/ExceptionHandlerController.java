package org.nomarch.movieland.web.controller;

import org.nomarch.movieland.exception.EnrichmentException;
import org.nomarch.movieland.exception.IncorrectCredentialsException;
import org.nomarch.movieland.exception.InsufficientAccessRightsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@SuppressWarnings("SameReturnValue")
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(IncorrectCredentialsException.class)
    public HttpStatus unauthorized(IncorrectCredentialsException e) {
        return HttpStatus.UNAUTHORIZED;
    }

    @ExceptionHandler(InsufficientAccessRightsException.class)
    public HttpStatus forbidden(InsufficientAccessRightsException e) {
        return HttpStatus.FORBIDDEN;
    }

    @ExceptionHandler(EnrichmentException.class)
    public HttpStatus serviceUnavailable(EnrichmentException e) {
        return HttpStatus.SERVICE_UNAVAILABLE;
    }
}
