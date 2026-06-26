package com.devsu.banking.config.exception;

public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String resource, String field, Object value) {
        super(resource + " ya existe con " + field + ": " + value);
    }
}
