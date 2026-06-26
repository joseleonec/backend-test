package com.devsu.banking.controller;

import com.devsu.banking.config.exception.CupoDiarioExcedidoException;
import com.devsu.banking.config.exception.SaldoInsuficienteException;
import com.devsu.banking.dto.MovimientoByNumeroCuentaRequestDto;
import com.devsu.banking.dto.MovimientoResponseDto;
import com.devsu.banking.service.MovimientoService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovimientoControllerTest {

    @Mock
    private MovimientoService movimientoService;

    @InjectMocks
    private MovimientoController movimientoController;

    @Test
    void postMovimiento_creditoValido_returns201() {
        var request = new MovimientoByNumeroCuentaRequestDto("478758", "CREDITO", new BigDecimal("500.00"));
        var response = new MovimientoResponseDto(
                1L, LocalDateTime.now(), "CREDITO",
                new BigDecimal("500.00"), new BigDecimal("2500.00"), 1L, "478758");

        when(movimientoService.registrarPorNumeroCuenta(any())).thenReturn(response);

        ResponseEntity<MovimientoResponseDto> result = movimientoController.create(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("CREDITO", result.getBody().tipoMovimiento());
        assertEquals(new BigDecimal("2500.00"), result.getBody().saldo());
    }

    @Test
    void postMovimiento_saldoInsuficiente_propagatesException() {
        var request = new MovimientoByNumeroCuentaRequestDto("478758", "DEBITO", new BigDecimal("5000.00"));
        when(movimientoService.registrarPorNumeroCuenta(any())).thenThrow(new SaldoInsuficienteException());

        assertThrows(SaldoInsuficienteException.class, () -> movimientoController.create(request));
    }

    @Test
    void postMovimiento_cupoDiarioExcedido_propagatesException() {
        var request = new MovimientoByNumeroCuentaRequestDto("478758", "DEBITO", new BigDecimal("999.00"));
        when(movimientoService.registrarPorNumeroCuenta(any())).thenThrow(new CupoDiarioExcedidoException());

        assertThrows(CupoDiarioExcedidoException.class, () -> movimientoController.create(request));
    }

    @Test
    void getMovimiento_byId_returns200() {
        var response = new MovimientoResponseDto(
                1L, LocalDateTime.now(), "CREDITO",
                new BigDecimal("200.00"), new BigDecimal("700.00"), 1L, "478758");

        when(movimientoService.findById(1L)).thenReturn(response);

        ResponseEntity<MovimientoResponseDto> result = movimientoController.findById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1L, result.getBody().id());
    }
}
