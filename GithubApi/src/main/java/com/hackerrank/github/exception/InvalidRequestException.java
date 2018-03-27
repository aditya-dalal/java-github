package com.hackerrank.github.exception;

import lombok.Getter;

@Getter
public class InvalidRequestException extends Exception {
    private String message;
    private int status;

    public InvalidRequestException(String message, int status) {
        super(message);
        this.message = message;
        this.status = status;
    }

}
