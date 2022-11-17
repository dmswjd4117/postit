package com.spring.boot.common.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class ApiError {
    private String message;
    private int status;

    public ApiError(Throwable throwable, HttpStatus status) {
        this.message = throwable.getMessage();
        this.status = status.value();
    }

    public ApiError(String message, HttpStatus status) {
        this.message = message;
        this.status = status.value();
    }
}
