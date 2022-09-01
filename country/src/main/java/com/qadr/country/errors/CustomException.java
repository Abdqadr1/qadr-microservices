package com.qadr.country.errors;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomException extends RuntimeException{
    private final HttpStatus status;

    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
    public CustomException(String message, HttpStatus status, Throwable throwable){
        super(message, throwable);
        this.status = status;
    }

}
