package com.github.cb2222124.vlpms.backend.exception;

import org.springframework.http.HttpStatusCode;

public class ListingException extends RuntimeException {

    private final HttpStatusCode statusCode;

    public ListingException(HttpStatusCode statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }
}
