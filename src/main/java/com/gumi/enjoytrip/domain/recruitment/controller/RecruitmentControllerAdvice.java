package com.gumi.enjoytrip.domain.recruitment.controller;

import com.gumi.enjoytrip.domain.recruitment.exception.RecruitmentNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RecruitmentControllerAdvice {
    @ExceptionHandler(RecruitmentNotFoundException.class)
    public ResponseEntity<Void> handleRecruitmentNotFountException(RecruitmentNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
