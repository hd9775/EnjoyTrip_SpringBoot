package com.gumi.enjoytrip.domain.tourinfo.controller;

import com.gumi.enjoytrip.domain.tourinfo.exception.InvalidSidoCodeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TourControllerAdvice {
    // 400 Bad Request
    @ExceptionHandler(InvalidSidoCodeException.class)
    public ResponseEntity<Void> handleInvalidSidoCodeException(InvalidSidoCodeException e) {
        return ResponseEntity.badRequest().build();
    }
}
