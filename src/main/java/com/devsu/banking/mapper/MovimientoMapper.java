package com.devsu.banking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.devsu.banking.dto.MovimientoRequestDto;
import com.devsu.banking.dto.MovimientoResponseDto;
import com.devsu.banking.entity.Movimiento;

@Mapper(componentModel = "spring")
public interface MovimientoMapper {

    @Mapping(source = "cuenta.id", target = "cuentaId")
    @Mapping(source = "cuenta.numeroCuenta", target = "numeroCuenta")
    MovimientoResponseDto toDto(Movimiento entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cuenta", ignore = true)
    @Mapping(target = "saldo", ignore = true)
    @Mapping(target = "saldoInicial", ignore = true)
    @Mapping(target = "fecha", ignore = true)
    Movimiento toEntity(MovimientoRequestDto dto);
}
