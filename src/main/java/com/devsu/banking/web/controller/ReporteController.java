package com.devsu.banking.web.controller;

import java.time.LocalDate;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.banking.domain.dto.ReporteDto;
import com.devsu.banking.domain.service.ReporteService;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    /**
     * GET /api/reportes?fecha=2024-01-01,2024-12-31&cliente=1 Optional:
     * &format=pdf or Accept: application/pdf
     */
    @GetMapping
    public ResponseEntity<?> reporte(
            @RequestParam("fecha") String fechaRango,
            @RequestParam("cliente") Long clienteId,
            @RequestParam(value = "format", required = false, defaultValue = "json") String format,
            @RequestHeader(value = HttpHeaders.ACCEPT, required = false,
                    defaultValue = "application/json") String accept
    ) {
        String[] parts = fechaRango.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("El parámetro 'fecha' debe tener formato: yyyy-MM-dd,yyyy-MM-dd");
        }
        LocalDate desde = LocalDate.parse(parts[0].trim());
        LocalDate hasta = LocalDate.parse(parts[1].trim());

        boolean wantsPdf = "pdf".equalsIgnoreCase(format) || accept.contains("application/pdf");

        if (wantsPdf) {
            byte[] pdf = reporteService.generarReportePdf(clienteId, desde, hasta);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte.pdf")
                    .body(pdf);
        }

        ReporteDto reporte = reporteService.generarReporte(clienteId, desde, hasta);
        return ResponseEntity.ok(reporte);
    }
}
