package com.gumi.enjoytrip.domain.user.exception;

public class LoginUserNotFoundException extends RuntimeException {
    public LoginUserNotFoundException(String message) {
        super(message);
    }
}
