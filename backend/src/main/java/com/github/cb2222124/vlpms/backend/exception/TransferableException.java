package com.github.cb2222124.vlpms.backend.exception;

import org.springframework.http.HttpStatusCode;

public class TransferableException extends RuntimeException {

    private final HttpStatusCode statusCode;

    public TransferableException(HttpStatusCode statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }
}