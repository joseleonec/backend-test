package com.devsu.banking.web.controller;

import com.devsu.banking.domain.dto.CuentaRequestDto;
import com.devsu.banking.domain.dto.CuentaResponseDto;
import com.devsu.banking.domain.service.CuentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
