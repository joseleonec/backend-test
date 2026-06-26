package com.devsu.banking.dto;

import lombok.Builder;

@Builder
public record ErrorResponseDto(String timestamp, int status, String path, Object error) {

}
