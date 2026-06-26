package com.devsu.banking.dto;

public record ClienteResponseDto(
        Long id,
        String nombre,
        String genero,
        Integer edad,
        String identificacion,
        String direccion,
        String telefono,
        String clienteid,
        Boolean estado
        ) {

}
