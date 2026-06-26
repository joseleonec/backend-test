package com.devsu.banking.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ReporteDto(
        String clienteId,
        String clienteNombre,
        LocalDate desde,
        LocalDate hasta,
        List<CuentaReporteDto> cuentas
        ) {

    public record CuentaReporteDto(
            String numeroCuenta,
            String tipoCuenta,
            BigDecimal saldoDisponible,
            BigDecimal totalCreditos,
            BigDecimal totalDebitos,
            List<MovimientoResponseDto> movimientos
            ) {

    }
}
