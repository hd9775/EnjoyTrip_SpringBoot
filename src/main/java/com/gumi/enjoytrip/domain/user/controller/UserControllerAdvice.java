package com.gumi.enjoytrip.domain.user.controller;

import com.gumi.enjoytrip.domain.user.exception.DuplicateEmailException;
import com.gumi.enjoytrip.domain.user.exception.InvalidPasswordException;
import com.gumi.enjoytrip.domain.user.exception.LoginUserNotFoundException;
import com.gumi.enjoytrip.domain.user.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserControllerAdvice {
    // 404 Not Found
    @ExceptionHandler({UserNotFoundException.class, LoginUserNotFoundException.class})
    public ResponseEntity<Void> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    // 401 Unauthorized
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Void> handleInvalidPasswordException(InvalidPasswordException e) {
        return ResponseEntity.status(401).build();
    }

    // 409 Conflict
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<Void> handleDuplicateEmailException(DuplicateEmailException e) {
        return ResponseEntity.status(409).build();
    }
}
