package com.devsu.banking.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CuentaRequestDto(
        @NotBlank
        @Size(max = 20)
        String numeroCuenta,
        @NotBlank
        @Size(max = 20)
        String tipoCuenta,
        @NotNull
        @DecimalMin("0.00")
        BigDecimal saldoInicial,
        Boolean estado,
        @NotBlank
        String clienteid
        ) {

}
