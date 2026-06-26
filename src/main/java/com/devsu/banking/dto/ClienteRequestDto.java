package com.devsu.banking.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteRequestDto(
        @NotBlank
        @Size(max = 100)
        String nombre,
        @NotBlank
        @Size(max = 20)
        String genero,
        @Min(0)
        @Max(150)
        Integer edad,
        @NotBlank
        @Size(max = 20)
        String identificacion,
        @Size(max = 200)
        String direccion,
        @Size(max = 20)
        String telefono,
        @NotBlank
        @Size(max = 50)
        String clienteid,
        @NotBlank
        String contrasena,
        Boolean estado
        ) {

}
