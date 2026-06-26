package com.devsu.banking.service;

import java.util.List;

import com.devsu.banking.dto.MovimientoByNumeroCuentaRequestDto;
import com.devsu.banking.dto.MovimientoRequestDto;
import com.devsu.banking.dto.MovimientoResponseDto;

public interface MovimientoService {

    List<MovimientoResponseDto> findAll();

    MovimientoResponseDto findById(Long id);

    MovimientoResponseDto registrar(MovimientoRequestDto dto);

    MovimientoResponseDto registrarPorNumeroCuenta(MovimientoByNumeroCuentaRequestDto dto);

    MovimientoResponseDto update(Long id, MovimientoRequestDto dto);

    void delete(Long id);
}
