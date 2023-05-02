package com.gumi.enjoytrip.domain;

import com.gumi.enjoytrip.domain.exception.NoSuchPermissionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    // 403 Forbidden
    @ExceptionHandler(NoSuchPermissionException.class)
    public ResponseEntity<Void> handleNoSuchPermissionException(NoSuchPermissionException e) {
        return ResponseEntity.status(403).build();
    }

    // 400 Bad Request
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }
}
