package com.books.exceptions;

public class UncorrectedQueryException extends Exception {
    public UncorrectedQueryException() {
        super();
    }

    public UncorrectedQueryException(String message) {
        super(message);
    }
}
