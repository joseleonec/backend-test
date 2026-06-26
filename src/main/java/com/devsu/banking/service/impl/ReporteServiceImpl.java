package com.devsu.banking.service.impl;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.banking.config.exception.ResourceNotFoundException;
import com.devsu.banking.dto.MovimientoResponseDto;
import com.devsu.banking.dto.ReporteDto;
import com.devsu.banking.entity.Cuenta;
import com.devsu.banking.entity.Movimiento;
import com.devsu.banking.mapper.MovimientoMapper;
import com.devsu.banking.repository.ClienteRepository;
import com.devsu.banking.repository.CuentaRepository;
import com.devsu.banking.repository.MovimientoRepository;
import com.devsu.banking.service.ReporteService;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReporteServiceImpl implements ReporteService {

    private final ClienteRepository clienteRepository;
    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;
    private final MovimientoMapper movimientoMapper;

    @Override
    public ReporteDto generarReporte(String clienteid, LocalDate desde, LocalDate hasta) {

        var cliente = clienteRepository.findByClienteid(clienteid)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", clienteid));

        var dateFrom = desde.atStartOfDay();
        var dateTo = hasta.plusDays(1).atStartOfDay();

        List<Movimiento> movimientos = movimientoRepository
                .findByCuentaClienteIdAndFechaBetween(cliente.getId(), dateFrom, dateTo);

        Map<Cuenta, List<Movimiento>> byCuenta = movimientos.stream()
                .collect(Collectors.groupingBy(Movimiento::getCuenta));

        List<ReporteDto.CuentaReporteDto> cuentasDto = cuentaRepository.findByClienteClienteid(clienteid)
                .stream()
                .map(cuenta -> buildCuentaReporte(cuenta, byCuenta.getOrDefault(cuenta, List.of())))
                .toList();

        return new ReporteDto(cliente.getClienteid(), cliente.getNombre(), desde, hasta, cuentasDto);
    }

    @Override
    public byte[] generarReportePdf(String clienteid, LocalDate desde, LocalDate hasta) {

        ReporteDto reporte = generarReporte(clienteid, desde, hasta);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (Document doc = new Document(PageSize.A4)) {
                PdfWriter.getInstance(doc, baos);
                doc.open();

                Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, Color.DARK_GRAY);
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
                Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

                doc.add(new Paragraph("Estado de Cuenta", titleFont));
                doc.add(new Paragraph("Cliente: " + reporte.clienteNombre(), headerFont));
                doc.add(new Paragraph("Período: " + reporte.desde() + " — " + reporte.hasta(), bodyFont));
                doc.add(Chunk.NEWLINE);

                for (ReporteDto.CuentaReporteDto cuenta : reporte.cuentas()) {
                    doc.add(new Paragraph(
                            "Cuenta: " + cuenta.numeroCuenta() + " (" + cuenta.tipoCuenta() + ")", headerFont));
                    doc.add(new Paragraph("Saldo disponible: " + cuenta.saldoDisponible(), bodyFont));
                    doc.add(new Paragraph("Total créditos: " + cuenta.totalCreditos(), bodyFont));
                    doc.add(new Paragraph("Total débitos: " + cuenta.totalDebitos(), bodyFont));
                    doc.add(Chunk.NEWLINE);

                    for (MovimientoResponseDto mov : cuenta.movimientos()) {
                        doc.add(new Paragraph(
                                "  " + mov.fecha() + " | " + mov.tipoMovimiento()
                                + " | Valor: " + mov.valor()
                                + " | Saldo inicial: " + mov.saldoInicial()
                                + " | Saldo disponible: " + mov.saldo(), bodyFont));
                    }
                    doc.add(Chunk.NEWLINE);
                }
            }

            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF report", e);
        }
    }

    private ReporteDto.CuentaReporteDto buildCuentaReporte(Cuenta cuenta, List<Movimiento> movs) {

        List<MovimientoResponseDto> movsDtos = movs.stream().map(movimientoMapper::toDto).toList();

        BigDecimal totalCreditos = movs.stream()
                .filter(m -> "CREDITO".equals(m.getTipoMovimiento()))
                .map(Movimiento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDebitos = movs.stream()
                .filter(m -> "DEBITO".equals(m.getTipoMovimiento()))
                .map(Movimiento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ReporteDto.CuentaReporteDto(
                cuenta.getNumeroCuenta(),
                cuenta.getTipoCuenta(),
                cuenta.getSaldoActual(),
                totalCreditos,
                totalDebitos,
                movsDtos
        );
    }
}
