package com.devsu.banking.domain.service;

import java.util.List;

import com.devsu.banking.domain.dto.CuentaRequestDto;
import com.devsu.banking.domain.dto.CuentaResponseDto;

public interface CuentaService {

    List<CuentaResponseDto> findAll();

    CuentaResponseDto findById(Long id);

    CuentaResponseDto create(CuentaRequestDto dto);

    CuentaResponseDto update(Long id, CuentaRequestDto dto);

    CuentaResponseDto patch(Long id, CuentaRequestDto dto);

    void delete(Long id);
}
