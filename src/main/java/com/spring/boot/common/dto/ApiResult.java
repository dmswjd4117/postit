package com.spring.boot.common.dto;

import org.springframework.http.HttpStatus;

public class ApiResult<T> {
    private final boolean success;

    private final T response;

    private final ApiError error;

    public ApiResult(boolean success, T response, ApiError error) {
        this.success = success;
        this.response = response;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getResponse() {
        return response;
    }

    public ApiError getError() {
        return error;
    }

    public static <T> ApiResult<T> success(T response){
        return new ApiResult<>(true, response, null);
    }

    public static ApiResult<?> error(Throwable throwable, HttpStatus status){
        return new ApiResult<>(false, null, new ApiError(throwable, status));
    }

    public static ApiResult<?> error(String msg, HttpStatus status){
        return new ApiResult<>(false, null, new ApiError(msg, status));
    }
}
