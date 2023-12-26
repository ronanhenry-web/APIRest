package com.example.example.controller.handler;

import com.example.example.utils.exceptions.FunctionalRuntimeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(FunctionalRuntimeException.class)
    public ResponseEntity<Object> handleFunctionalRuntimeException(final FunctionalRuntimeException functionalRuntimeException, final WebRequest request) {
        return handleExceptionInternal(
                functionalRuntimeException,
                functionalRuntimeException.getMessage(),
                new HttpHeaders(),
                functionalRuntimeException.getType().getHttpStatus(),
                request);
    }
}
