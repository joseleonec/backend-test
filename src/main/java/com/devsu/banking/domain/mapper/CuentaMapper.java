package com.devsu.banking.domain.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.devsu.banking.domain.dto.CuentaRequestDto;
import com.devsu.banking.domain.dto.CuentaResponseDto;
import com.devsu.banking.domain.entity.Cuenta;

@Mapper(componentModel = "spring")
public interface CuentaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    Cuenta toEntity(CuentaRequestDto dto);

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nombre", target = "clienteNombre")
    CuentaResponseDto toDto(Cuenta entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    void updateFromDto(CuentaRequestDto dto, @MappingTarget Cuenta entity);
}
