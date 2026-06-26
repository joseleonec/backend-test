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

import com.devsu.banking.dto.CuentaRequestDto;
import com.devsu.banking.dto.CuentaResponseDto;
import com.devsu.banking.service.CuentaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;

    @GetMapping
    public ResponseEntity<List<CuentaResponseDto>> findAll() {
        return ResponseEntity.ok(cuentaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CuentaResponseDto> create(@Valid @RequestBody CuentaRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaResponseDto> update(@PathVariable Long id,
            @Valid @RequestBody CuentaRequestDto dto) {
        return ResponseEntity.ok(cuentaService.update(id, dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CuentaResponseDto> patch(@PathVariable Long id,
            @RequestBody CuentaRequestDto dto) {
        return ResponseEntity.ok(cuentaService.patch(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cuentaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
