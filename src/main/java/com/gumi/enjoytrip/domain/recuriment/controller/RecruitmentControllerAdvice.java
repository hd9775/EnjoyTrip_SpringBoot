package com.gumi.enjoytrip.domain.recuriment.controller;

import com.gumi.enjoytrip.domain.recuriment.exception.RecruitmentNotFountException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RecruitmentControllerAdvice {
    @ExceptionHandler(RecruitmentNotFountException.class)
    public ResponseEntity<Void> handleRecruitmentNotFountException(RecruitmentNotFountException e) {
        return ResponseEntity.notFound().build();
    }
}
