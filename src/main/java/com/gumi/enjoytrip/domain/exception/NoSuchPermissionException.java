package com.gumi.enjoytrip.domain.exception;

public class NoSuchPermissionException extends RuntimeException {
    public NoSuchPermissionException(String message) {
        super(message);
    }
}
