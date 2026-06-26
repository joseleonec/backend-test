package com.devsu.banking.domain.dto;

import lombok.Builder;

@Builder
public record ErrorResponseDto(String timestamp, int status, String path, Object error) {

}
