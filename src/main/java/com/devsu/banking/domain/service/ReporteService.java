package com.devsu.banking.domain.service;

import java.time.LocalDate;

import com.devsu.banking.domain.dto.ReporteDto;

public interface ReporteService {

    ReporteDto generarReporte(String clienteid, LocalDate desde, LocalDate hasta);

    byte[] generarReportePdf(String clienteid, LocalDate desde, LocalDate hasta);
}
