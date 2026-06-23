package com.devsu.banking.web.controller;

import com.devsu.banking.config.exception.ResourceNotFoundException;
import com.devsu.banking.domain.dto.ClienteRequestDto;
import com.devsu.banking.domain.dto.ClienteResponseDto;
import com.devsu.banking.domain.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @Test
    void getCliente_existente_returns200() {
        var dto = new ClienteResponseDto(1L, "Juan Perez", "M", 30,
                "1234567890", "Calle 1", "099", "juanp", true);

        when(clienteService.findById(1L)).thenReturn(dto);

        ResponseEntity<ClienteResponseDto> result = clienteController.findById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Juan Perez", result.getBody().nombre());
        assertEquals("juanp", result.getBody().clienteid());
    }

    @Test
    void getCliente_noExistente_propagatesNotFoundException() {
        when(clienteService.findById(99L)).thenThrow(new ResourceNotFoundException("Cliente", 99L));

        assertThrows(ResourceNotFoundException.class, () -> clienteController.findById(99L));
    }

    @Test
    void postCliente_datosValidos_returns201() {
        var dto = new ClienteRequestDto("Ana Lopez", "F", 28, "9876543210",
                null, null, "analopez", "secret123", true);
        var response = new ClienteResponseDto(2L, "Ana Lopez", "F", 28,
                "9876543210", null, null, "analopez", true);

        when(clienteService.create(dto)).thenReturn(response);

        ResponseEntity<ClienteResponseDto> result = clienteController.create(dto);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals("Ana Lopez", result.getBody().nombre());
    }

    @Test
    void getAll_returnsListWith200() {
        var dto = new ClienteResponseDto(1L, "Juan", "M", 30,
                "111", null, null, "juan1", true);

        when(clienteService.findAll()).thenReturn(List.of(dto));

        ResponseEntity<List<ClienteResponseDto>> result = clienteController.findAll();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
    }
}
