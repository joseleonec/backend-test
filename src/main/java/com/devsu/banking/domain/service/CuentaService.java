package com.devsu.banking.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.banking.config.exception.ResourceNotFoundException;
import com.devsu.banking.domain.dto.CuentaRequestDto;
import com.devsu.banking.domain.dto.CuentaResponseDto;
import com.devsu.banking.domain.entity.Cliente;
import com.devsu.banking.domain.entity.Cuenta;
import com.devsu.banking.domain.mapper.CuentaMapper;
import com.devsu.banking.domain.repository.ClienteRepository;
import com.devsu.banking.domain.repository.CuentaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteRepository clienteRepository;
    private final CuentaMapper cuentaMapper;

    @Transactional(readOnly = true)
    public List<CuentaResponseDto> findAll() {
        return cuentaRepository.findAll().stream()
                .map(cuentaMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public CuentaResponseDto findById(Long id) {
        return cuentaMapper.toDto(getOrThrow(id));
    }

    public CuentaResponseDto create(CuentaRequestDto dto) {
        Cuenta cuenta = cuentaMapper.toEntity(dto);
        cuenta.setCliente(resolveCliente(dto.clienteId()));
        return cuentaMapper.toDto(cuentaRepository.save(cuenta));
    }

    public CuentaResponseDto update(Long id, CuentaRequestDto dto) {
        Cuenta cuenta = getOrThrow(id);
        cuentaMapper.updateFromDto(dto, cuenta);
        if (dto.clienteId() != null) {
            cuenta.setCliente(resolveCliente(dto.clienteId()));
        }
        return cuentaMapper.toDto(cuentaRepository.save(cuenta));
    }

    public CuentaResponseDto patch(Long id, CuentaRequestDto dto) {
        return update(id, dto);
    }

    public void delete(Long id) {
        Cuenta cuenta = getOrThrow(id);
        cuenta.setEstado(false);
        cuentaRepository.save(cuenta);
    }

    private Cuenta getOrThrow(Long id) {
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta", id));
    }

    private Cliente resolveCliente(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", clienteId));
    }
}
