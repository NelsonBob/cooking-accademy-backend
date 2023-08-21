package com.esgi.pa.domain.exceptions;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class TechnicalFoundException extends Exception {

    private final Map<String, Object> map = new HashMap<>();

    public TechnicalFoundException(String message) {
        super(message);
        this.map.put("message", message);
    }

    public TechnicalFoundException(String message, Object object) {
        super(message);
        this.map.put("message", message);
        this.map.put("data", object);
    }

}
