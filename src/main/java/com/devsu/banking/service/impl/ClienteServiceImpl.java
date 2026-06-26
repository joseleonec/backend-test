package com.devsu.banking.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.banking.config.exception.ResourceAlreadyExistsException;
import com.devsu.banking.config.exception.ResourceNotFoundException;
import com.devsu.banking.dto.ClienteRequestDto;
import com.devsu.banking.dto.ClienteResponseDto;
import com.devsu.banking.entity.Cliente;
import com.devsu.banking.mapper.ClienteMapper;
import com.devsu.banking.repository.ClienteRepository;
import com.devsu.banking.service.ClienteService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponseDto> findAll() {

        return clienteRepository.findAll().stream()
                .map(clienteMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDto findById(Long id) {

        return clienteMapper.toDto(getOrThrow(id));
    }

    @Override
    public ClienteResponseDto create(ClienteRequestDto dto) {

        if (clienteRepository.existsByClienteid(dto.clienteid())) {
            throw new ResourceAlreadyExistsException("Cliente", "clienteid", dto.clienteid());
        }

        if (clienteRepository.existsByIdentificacion(dto.identificacion())) {
            throw new ResourceAlreadyExistsException("Cliente", "identificacion", dto.identificacion());
        }

        var cliente = clienteMapper.toEntity(dto);
        cliente.setContrasena(passwordEncoder.encode(dto.contrasena()));

        return clienteMapper.toDto(clienteRepository.save(cliente));
    }

    @Override
    public ClienteResponseDto update(Long id, ClienteRequestDto dto) {

        var cliente = getOrThrow(id);

        clienteMapper.updateFromDto(dto, cliente);

        if (dto.contrasena() != null && !dto.contrasena().isBlank()) {
            cliente.setContrasena(passwordEncoder.encode(dto.contrasena()));
        }

        return clienteMapper.toDto(clienteRepository.save(cliente));
    }

    @Override
    public ClienteResponseDto patch(Long id, ClienteRequestDto dto) {

        return update(id, dto);
    }

    @Override
    public void delete(Long id) {

        clienteRepository.delete(getOrThrow(id));
    }

    private Cliente getOrThrow(Long id) {

        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", id));
    }
}
