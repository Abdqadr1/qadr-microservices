package com.qadr.gateway.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, ?>> handler(CustomException exception){
        Map<String, Object> error = new HashMap<>();
        error.put("message", exception.getMessage());
        error.put("status", exception.getStatus());
        error.put("timestamp", exception.getTimestamp());

        return ResponseEntity.status(exception.getStatus()).body(error);
    }
}
