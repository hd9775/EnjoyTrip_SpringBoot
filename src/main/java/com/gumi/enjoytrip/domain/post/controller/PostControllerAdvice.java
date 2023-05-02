package com.gumi.enjoytrip.domain.post.controller;

import com.gumi.enjoytrip.domain.post.exception.PostNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PostControllerAdvice {
    // 404 Not Found
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<Void> handlePostNotFoundException(PostNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
