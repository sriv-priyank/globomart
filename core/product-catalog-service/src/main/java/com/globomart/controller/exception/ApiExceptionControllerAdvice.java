package com.globomart.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public final class ApiExceptionControllerAdvice {

    @ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    ErrorMessage handleException(ApiException ex) {
        return new ErrorMessage(ex);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    ErrorMessage handleNotFound(RecordNotFoundException ex) {
        return new ErrorMessage(ex);
    }
}
