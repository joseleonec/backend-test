package com.devsu.banking.service;

import java.util.List;

import com.devsu.banking.dto.CuentaRequestDto;
import com.devsu.banking.dto.CuentaResponseDto;

public interface CuentaService {

    List<CuentaResponseDto> findAll();

    CuentaResponseDto findById(Long id);

    CuentaResponseDto create(CuentaRequestDto dto);

    CuentaResponseDto update(Long id, CuentaRequestDto dto);

    CuentaResponseDto patch(Long id, CuentaRequestDto dto);

    void delete(Long id);
}
