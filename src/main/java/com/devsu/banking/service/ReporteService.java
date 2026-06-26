package com.devsu.banking.service;

import java.time.LocalDate;

import com.devsu.banking.dto.ReporteDto;

public interface ReporteService {

    ReporteDto generarReporte(String clienteid, LocalDate desde, LocalDate hasta);

    byte[] generarReportePdf(String clienteid, LocalDate desde, LocalDate hasta);
}
