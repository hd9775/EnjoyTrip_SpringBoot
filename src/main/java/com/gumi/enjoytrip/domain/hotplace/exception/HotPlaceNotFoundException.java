package com.gumi.enjoytrip.domain.hotplace.exception;

public class HotPlaceNotFoundException extends RuntimeException{
    public HotPlaceNotFoundException(String message) {
        super(message);
    }
}
