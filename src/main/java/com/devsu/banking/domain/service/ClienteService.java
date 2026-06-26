package com.devsu.banking.domain.service;

import java.util.List;

import com.devsu.banking.domain.dto.ClienteRequestDto;
import com.devsu.banking.domain.dto.ClienteResponseDto;

public interface ClienteService {

    List<ClienteResponseDto> findAll();

    ClienteResponseDto findById(Long id);

    ClienteResponseDto create(ClienteRequestDto dto);

    ClienteResponseDto update(Long id, ClienteRequestDto dto);

    ClienteResponseDto patch(Long id, ClienteRequestDto dto);

    void delete(Long id);
}
