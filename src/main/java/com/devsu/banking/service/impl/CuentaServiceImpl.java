package com.devsu.banking.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.banking.config.exception.ResourceAlreadyExistsException;
import com.devsu.banking.config.exception.ResourceNotFoundException;
import com.devsu.banking.dto.CuentaRequestDto;
import com.devsu.banking.dto.CuentaResponseDto;
import com.devsu.banking.entity.Cliente;
import com.devsu.banking.entity.Cuenta;
import com.devsu.banking.mapper.CuentaMapper;
import com.devsu.banking.repository.ClienteRepository;
import com.devsu.banking.repository.CuentaRepository;
import com.devsu.banking.service.CuentaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteRepository clienteRepository;
    private final CuentaMapper cuentaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CuentaResponseDto> findAll() {

        return cuentaRepository.findAll().stream()
                .map(cuentaMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CuentaResponseDto findById(Long id) {

        return cuentaMapper.toDto(getOrThrow(id));
    }

    @Override
    public CuentaResponseDto create(CuentaRequestDto dto) {

        if (cuentaRepository.existsByNumeroCuenta(dto.numeroCuenta())) {
            throw new ResourceAlreadyExistsException("Cuenta", "numeroCuenta", dto.numeroCuenta());
        }

        var cuenta = cuentaMapper.toEntity(dto);
        cuenta.setCliente(resolveCliente(dto.clienteId()));

        return cuentaMapper.toDto(cuentaRepository.save(cuenta));
    }

    @Override
    public CuentaResponseDto update(Long id, CuentaRequestDto dto) {

        var cuenta = getOrThrow(id);

        cuentaMapper.updateFromDto(dto, cuenta);

        cuenta.setCliente(resolveCliente(dto.clienteId()));

        return cuentaMapper.toDto(cuentaRepository.save(cuenta));
    }

    @Override
    public CuentaResponseDto patch(Long id, CuentaRequestDto dto) {

        return update(id, dto);
    }

    @Override
    public void delete(Long id) {

        var cuenta = getOrThrow(id);
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
