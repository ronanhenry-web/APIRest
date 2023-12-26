package com.example.example.utils.exceptions;

import org.slf4j.Logger;

public class RuntimeExceptionFactory {
    public static FunctionalRuntimeException getFunctionalRuntimeException(final Logger logger, final FunctionalExceptionType type, final String message, final Object... arguments) {
        if (logger != null) {
            logger.error(String.format(message, arguments));
        }
        return new FunctionalRuntimeException(String.format(message, arguments), type);
    }

    public static TechnicalRuntimeException getTechnicalRuntimeException(final Logger logger, final String message, final Throwable cause) {
        if (logger != null) {
            logger.error(message, cause);
        }
        return new TechnicalRuntimeException(message, cause);
    }
}
