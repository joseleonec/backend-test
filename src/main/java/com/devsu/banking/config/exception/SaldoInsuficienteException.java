package com.devsu.banking.config.exception;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException() {
        super("Saldo no disponible");
    }
}
