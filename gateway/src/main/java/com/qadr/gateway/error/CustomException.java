package com.qadr.gateway.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


@AllArgsConstructor
@Data
public class CustomException extends RuntimeException{
    private HttpStatus status;
    private LocalDateTime timestamp;

    public CustomException(HttpStatus status, String message){
        super(message);
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

}
