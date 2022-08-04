package com.spring.boot.controller;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiError {
    private final String message;
    private final int status;

    public ApiError(Throwable throwable, HttpStatus status) {
        this.message = throwable.getMessage();
        this.status = status.value();
    }

    public ApiError(String message, HttpStatus status) {
        this.message = message;
        this.status = status.value();
    }
}
