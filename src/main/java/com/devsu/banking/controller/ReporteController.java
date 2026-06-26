package com.devsu.banking.controller;

import java.time.LocalDate;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.banking.dto.ReporteDto;
import com.devsu.banking.service.ReporteService;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    /**
     * GET /api/reportes?clienteid=joselema&fechaDesde=2024-01-01&fechaHasta=2024-12-31
     * Optional: Accept: application/pdf for PDF output
     */
    @GetMapping
    public ResponseEntity<?> reporte(
            @RequestParam("clienteid") String clienteid,
            @RequestParam("fechaDesde") LocalDate fechaDesde,
            @RequestParam("fechaHasta") LocalDate fechaHasta,
            @RequestHeader(value = HttpHeaders.ACCEPT, required = false,
                    defaultValue = "application/json") String accept
    ) {
        boolean wantsPdf = accept.contains("application/pdf");

        if (wantsPdf) {
            byte[] pdf = reporteService.generarReportePdf(clienteid, fechaDesde, fechaHasta);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte.pdf")
                    .body(pdf);
        }

        ReporteDto reporte = reporteService.generarReporte(clienteid, fechaDesde, fechaHasta);
        return ResponseEntity.ok(reporte);
    }
}
