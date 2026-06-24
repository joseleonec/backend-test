package com.devsu.banking.domain.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.banking.config.exception.CupoDiarioExcedidoException;
import com.devsu.banking.config.exception.ResourceNotFoundException;
import com.devsu.banking.config.exception.SaldoInsuficienteException;
import com.devsu.banking.domain.dto.MovimientoRequestDto;
import com.devsu.banking.domain.dto.MovimientoResponseDto;
import com.devsu.banking.domain.entity.Cuenta;
import com.devsu.banking.domain.entity.Movimiento;
import com.devsu.banking.domain.mapper.MovimientoMapper;
import com.devsu.banking.domain.repository.CuentaRepository;
import com.devsu.banking.domain.repository.MovimientoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;
    private final MovimientoMapper movimientoMapper;

    @Value("${banking.daily-withdrawal-limit:1000}")
    private BigDecimal dailyWithdrawalLimit;

    @Transactional(readOnly = true)
    public List<MovimientoResponseDto> findAll() {
        return movimientoRepository.findAll().stream()
                .map(movimientoMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public MovimientoResponseDto findById(Long id) {
        return movimientoMapper.toDto(getOrThrow(id));
    }

    public MovimientoResponseDto registrar(MovimientoRequestDto dto) {
        Cuenta cuenta = cuentaRepository.findById(dto.cuentaId())
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta", dto.cuentaId()));

        BigDecimal valor = normalizeValor(dto.tipoMovimiento(), dto.valor());
        BigDecimal nuevoSaldo = cuenta.getSaldoInicial().add(valor);

        if ("DEBITO".equals(dto.tipoMovimiento())) {
            if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
                throw new SaldoInsuficienteException();
            }
            checkDailyLimit(cuenta.getId(), valor.abs());
        }

        Movimiento movimiento = new Movimiento();
        movimiento.setCuenta(cuenta);
        movimiento.setTipoMovimiento(dto.tipoMovimiento());
        movimiento.setValor(valor);
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setFecha(LocalDateTime.now());

        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);
        return movimientoMapper.toDto(movimientoRepository.save(movimiento));
    }

    public MovimientoResponseDto update(Long id, MovimientoRequestDto dto) {
        Movimiento movimiento = getOrThrow(id);
        movimiento.setTipoMovimiento(dto.tipoMovimiento());
        movimiento.setValor(normalizeValor(dto.tipoMovimiento(), dto.valor()));
        return movimientoMapper.toDto(movimientoRepository.save(movimiento));
    }

    public void delete(Long id) {
        movimientoRepository.delete(getOrThrow(id));
    }

    private BigDecimal normalizeValor(String tipo, BigDecimal valor) {
        return "DEBITO".equals(tipo) ? valor.abs().negate() : valor.abs();
    }

    private void checkDailyLimit(Long cuentaId, BigDecimal montoAbsoluto) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        BigDecimal debitosHoy = movimientoRepository
                .sumDebitosDiarios(cuentaId, startOfDay, endOfDay).abs();
        if (debitosHoy.add(montoAbsoluto).compareTo(dailyWithdrawalLimit) > 0) {
            throw new CupoDiarioExcedidoException();
        }
    }

    private Movimiento getOrThrow(Long id) {
        return movimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento", id));
    }
}
