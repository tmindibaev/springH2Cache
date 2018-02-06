package com.h2test.sprngbt.exceptions;

public class NoSuchUserException extends RuntimeException {

    public  NoSuchUserException(String message) {
        super(message);
    }

    public NoSuchUserException() {
        super();
    }

    public  NoSuchUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
