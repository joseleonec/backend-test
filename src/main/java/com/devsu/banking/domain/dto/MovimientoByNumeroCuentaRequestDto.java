package com.devsu.banking.domain.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record MovimientoByNumeroCuentaRequestDto(
        @NotBlank
        String numeroCuenta,
        @NotBlank
        @Pattern(regexp = "CREDITO|DEBITO")
        String tipoMovimiento,
        @NotNull
        BigDecimal valor) {
}
