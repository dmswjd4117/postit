package com.spring.boot.controller;


import org.springframework.http.HttpStatus;

public class ApiError {

    private final String message;

    private final int status;

    public ApiError(Throwable throwable, HttpStatus status) {
        this.message = throwable.getMessage();
        this.status = status.value();
    }

}
