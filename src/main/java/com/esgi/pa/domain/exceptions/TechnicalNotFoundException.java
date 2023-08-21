package com.esgi.pa.domain.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
public class TechnicalNotFoundException extends Exception {

    private HttpStatus status;
    private final Map<String, Object> map = new HashMap<>();

    public TechnicalNotFoundException(String message) {
        super(message);
    }

    public TechnicalNotFoundException(HttpStatus statusCode, String message, Object object) {
        this.status = statusCode;
        this.map.put("message", message);
        this.map.put("data", object);
    }

    public TechnicalNotFoundException(HttpStatus statusCode, String message) {
        this.status = statusCode;
        this.map.put("message", message);
    }
}
