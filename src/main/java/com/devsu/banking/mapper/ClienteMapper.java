package com.devsu.banking.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.devsu.banking.dto.ClienteRequestDto;
import com.devsu.banking.dto.ClienteResponseDto;
import com.devsu.banking.entity.Cliente;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contrasena", ignore = true)
    Cliente toEntity(ClienteRequestDto dto);

    ClienteResponseDto toDto(Cliente entity);

    /**
     * Partial update — only non-null record fields are applied.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contrasena", ignore = true)
    void updateFromDto(ClienteRequestDto dto, @MappingTarget Cliente entity);
}
