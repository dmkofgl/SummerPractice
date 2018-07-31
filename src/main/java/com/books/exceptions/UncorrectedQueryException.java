package com.books.exceptions;

public class UncorrectedQueryException extends RuntimeException {
    public UncorrectedQueryException() {
        super();
    }

    public UncorrectedQueryException(String message) {
        super(message);
    }
}
