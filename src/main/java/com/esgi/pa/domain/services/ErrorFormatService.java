package com.esgi.pa.domain.services;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service de formatage des erreurs
 */
@Service
public class ErrorFormatService {

    /**
     * Formate les erreurs
     *
     * @param bindingResult contient les erreurs
     * @return les erreurs formattées
     */
    public Map<String, Object> ErrorFormatExceptionHandle(BindingResult bindingResult) {
        List<ObjectError> errors = bindingResult.getAllErrors();
        Map<String, Object> map = new HashMap<>();
        List<FieldError> fieldErrors = errors.stream()
            .filter(FieldError.class::isInstance)
            .map(FieldError.class::cast)
            .toList();

        List<String> errorMessages = fieldErrors.stream()
            .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
            .collect(Collectors.toList());

        map.put("message", "Validation Failed");
        map.put("data", errorMessages);
        return map;
    }
}
