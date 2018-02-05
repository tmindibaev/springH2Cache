package com.h2test.sprngbt.exceptions;

public class NoSuchUserException extends RuntimeException {
    private Long key;

    public NoSuchUserException(Long key) {
        this.key = key;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }
}
