package com.devsu.banking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimientoResponseDto(
        Long id,
        LocalDateTime fecha,
        String tipoMovimiento,
        BigDecimal valor,
        BigDecimal saldoInicial,
        BigDecimal saldo,
        Long cuentaId,
        String numeroCuenta
        ) {

}
