package com.hylamobile.promotion.exceptions;

import org.springframework.core.NestedRuntimeException;

public class CurrencyMismatchException extends NestedRuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public CurrencyMismatchException() {
        super("The currencies do not match");
    }

    public CurrencyMismatchException(String message) {
        super(message);
    }

    public CurrencyMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public CurrencyMismatchException(Throwable cause) {
        super("The currencies do not match", cause);
    }
}

