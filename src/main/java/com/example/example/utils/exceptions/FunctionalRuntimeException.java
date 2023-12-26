package com.example.example.utils.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FunctionalRuntimeException extends RuntimeException {
    private FunctionalExceptionType type;

    public FunctionalRuntimeException(String message, FunctionalExceptionType type) {
        super(message);
        this.type = type;
    }
}