package com.devsu.banking.web.controller;

import com.devsu.banking.domain.dto.MovimientoRequestDto;
import com.devsu.banking.domain.dto.MovimientoResponseDto;
import com.devsu.banking.domain.service.MovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<MovimientoResponseDto> create(@Valid @RequestBody MovimientoRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoService.registrar(dto));
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
