package com.spring.boot.common.error;

import com.spring.boot.common.dto.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private ResponseEntity<ApiResult<?>> newResponse(Throwable throwable, HttpStatus httpStatus){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(ApiResult.error(throwable, httpStatus), headers, httpStatus);
    }

    @ExceptionHandler(ServiceRuntimeException.class)
    private ResponseEntity<?> handleServiceRuntimeException(ServiceRuntimeException exception){
        if(exception instanceof DuplicatedException){
            return newResponse(exception, HttpStatus.BAD_REQUEST);
        }
        return newResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({
            IllegalArgumentException.class, IllegalStateException.class
    })
    private ResponseEntity<?> handleBadRequestException(RuntimeException exception){
        log.debug("Bad request exception : {}", exception.getMessage());
        return newResponse(exception, HttpStatus.BAD_REQUEST);
    }
}
