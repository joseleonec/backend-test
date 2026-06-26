package com.devsu.banking.domain.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.banking.config.exception.ResourceAlreadyExistsException;
import com.devsu.banking.config.exception.ResourceNotFoundException;
import com.devsu.banking.domain.dto.ClienteRequestDto;
import com.devsu.banking.domain.dto.ClienteResponseDto;
import com.devsu.banking.domain.entity.Cliente;
import com.devsu.banking.domain.mapper.ClienteMapper;
import com.devsu.banking.domain.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<ClienteResponseDto> findAll() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClienteResponseDto findById(Long id) {
        return clienteMapper.toDto(getOrThrow(id));
    }

    public ClienteResponseDto create(ClienteRequestDto dto) {
        if (clienteRepository.existsByClienteid(dto.clienteid())) {
            throw new ResourceAlreadyExistsException("Cliente", "clienteid", dto.clienteid());
        }
        if (clienteRepository.existsByIdentificacion(dto.identificacion())) {
            throw new ResourceAlreadyExistsException("Cliente", "identificacion", dto.identificacion());
        }
        Cliente cliente = clienteMapper.toEntity(dto);
        cliente.setContrasena(passwordEncoder.encode(dto.contrasena()));
        return clienteMapper.toDto(clienteRepository.save(cliente));
    }

    public ClienteResponseDto update(Long id, ClienteRequestDto dto) {
        Cliente cliente = getOrThrow(id);
        clienteMapper.updateFromDto(dto, cliente);
        if (dto.contrasena() != null && !dto.contrasena().isBlank()) {
            cliente.setContrasena(passwordEncoder.encode(dto.contrasena()));
        }
        return clienteMapper.toDto(clienteRepository.save(cliente));
    }

    public ClienteResponseDto patch(Long id, ClienteRequestDto dto) {
        return update(id, dto);
    }

    public void delete(Long id) {
        clienteRepository.delete(getOrThrow(id));
    }

    private Cliente getOrThrow(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", id));
    }
}
