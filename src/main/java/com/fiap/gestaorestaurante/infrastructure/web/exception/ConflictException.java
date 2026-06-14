package com.fiap.gestaorestaurante.infrastructure.web.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
