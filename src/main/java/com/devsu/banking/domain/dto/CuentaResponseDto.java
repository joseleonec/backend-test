package com.devsu.banking.domain.dto;

import java.math.BigDecimal;

public record CuentaResponseDto(
        Long id,
        String numeroCuenta,
        String tipoCuenta,
        BigDecimal saldoInicial,
        Boolean estado,
        Long clienteId,
        String clienteNombre
        ) {

}
