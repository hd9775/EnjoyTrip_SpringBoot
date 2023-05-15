package com.gumi.enjoytrip.domain.participant.controller;

import com.gumi.enjoytrip.domain.participant.exception.ParticipantNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ParticipantControllerAdvice {
    @ExceptionHandler(ParticipantNotFoundException.class)
    public ResponseEntity<Void> handleParticipantNotFoundException(ParticipantNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
