package com.example.musicstorerecommendations.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class NotFoundException extends RuntimeException {
    public NotFoundException(){super();}
    public NotFoundException(String msg) {super(msg);}
}