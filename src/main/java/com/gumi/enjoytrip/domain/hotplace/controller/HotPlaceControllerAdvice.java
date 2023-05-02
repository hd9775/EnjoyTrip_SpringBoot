package com.gumi.enjoytrip.domain.hotplace.controller;

import com.gumi.enjoytrip.domain.hotplace.exception.HotPlaceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HotPlaceControllerAdvice {
    // 404 Not Found
    @ExceptionHandler(HotPlaceNotFoundException.class)
    public ResponseEntity<Void> handleHotPlaceNotFoundException(HotPlaceNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
