package com.devsu.banking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.banking.dto.MovimientoByNumeroCuentaRequestDto;
import com.devsu.banking.dto.MovimientoRequestDto;
import com.devsu.banking.dto.MovimientoResponseDto;
import com.devsu.banking.service.MovimientoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @GetMapping
    public ResponseEntity<List<MovimientoResponseDto>> findAll() {
        return ResponseEntity.ok(movimientoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<MovimientoResponseDto> create(
            @Valid @RequestBody MovimientoByNumeroCuentaRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(movimientoService.registrarPorNumeroCuenta(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimientoResponseDto> update(@PathVariable Long id,
            @Valid @RequestBody MovimientoRequestDto dto) {
        return ResponseEntity.ok(movimientoService.update(id, dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MovimientoResponseDto> patch(@PathVariable Long id,
            @RequestBody MovimientoRequestDto dto) {
        return ResponseEntity.ok(movimientoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movimientoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
