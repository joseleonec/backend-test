package com.devsu.banking.domain.service;

import java.time.LocalDate;

import com.devsu.banking.domain.dto.ReporteDto;

public interface ReporteService {

    ReporteDto generarReporte(Long clienteId, LocalDate desde, LocalDate hasta);

    byte[] generarReportePdf(Long clienteId, LocalDate desde, LocalDate hasta);
}
