package com.esgi.pa.api.error;

import com.esgi.pa.domain.exceptions.ArgumentNotValidException;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.ErrorFormatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorHandler {

    private final ErrorFormatService errorFormatService;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TechnicalNotFoundException.class)
    public ErrorDto handleNotFoundException(TechnicalNotFoundException ex) {
        return new ErrorDto(ex.getMap());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDto handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ArgumentNotValidException e = new ArgumentNotValidException(errorFormatService.ErrorFormatExceptionHandle(ex));
        return new ErrorDto(e.getErrors());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TechnicalFoundException.class)
    public ErrorDto handleNotFoundException(TechnicalFoundException ex) {
        return new ErrorDto(ex.getMap());
    }
}
