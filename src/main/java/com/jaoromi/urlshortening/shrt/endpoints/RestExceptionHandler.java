package com.jaoromi.urlshortening.shrt.endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler( { IllegalArgumentException.class })
    public ResponseEntity<?> handleBadRequest(HttpServletRequest req, Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("ERROR", e.getMessage()).build();
    }
}
