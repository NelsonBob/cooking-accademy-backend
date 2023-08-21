package com.esgi.pa.domain.exceptions;

import lombok.Getter;

import java.util.Map;

@Getter
public class ArgumentNotValidException extends RuntimeException {

    private final Map<String, Object> errors;

    public ArgumentNotValidException(Map<String, Object> errors) {
        super("Validation failed");
        this.errors = errors;
    }

}
