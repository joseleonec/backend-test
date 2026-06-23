package com.devsu.banking.config.exception;

public class CupoDiarioExcedidoException extends RuntimeException {
    public CupoDiarioExcedidoException() {
        super("Cupo diario Excedido");
    }
}
