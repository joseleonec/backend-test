package com.devsu.banking.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.devsu.banking.domain.dto.MovimientoResponseDto;
import com.devsu.banking.domain.entity.Movimiento;

@Mapper(componentModel = "spring")
public interface MovimientoMapper {

    @Mapping(source = "cuenta.id", target = "cuentaId")
    @Mapping(source = "cuenta.numeroCuenta", target = "numeroCuenta")
    MovimientoResponseDto toDto(Movimiento entity);
}
