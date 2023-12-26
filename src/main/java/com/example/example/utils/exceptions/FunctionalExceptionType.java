package com.example.example.utils.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum FunctionalExceptionType {
    NOT_FOUND(HttpStatus.NOT_FOUND);

    private final HttpStatus httpStatus;

    FunctionalExceptionType(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
