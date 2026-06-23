package com.devsu.banking.web.controller;

import com.devsu.banking.domain.dto.ClienteRequestDto;
import com.devsu.banking.domain.dto.ClienteResponseDto;
import com.devsu.banking.domain.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> findAll() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDto> create(@Valid @RequestBody ClienteRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> update(@PathVariable Long id,
                                                     @Valid @RequestBody ClienteRequestDto dto) {
        return ResponseEntity.ok(clienteService.update(id, dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> patch(@PathVariable Long id,
                                                    @RequestBody ClienteRequestDto dto) {
        return ResponseEntity.ok(clienteService.patch(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
