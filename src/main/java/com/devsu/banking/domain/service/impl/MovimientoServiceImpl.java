package com.devsu.banking.domain.service.impl;

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
import com.devsu.banking.domain.entity.Movimiento;
import com.devsu.banking.domain.mapper.MovimientoMapper;
import com.devsu.banking.domain.repository.CuentaRepository;
import com.devsu.banking.domain.repository.MovimientoRepository;
import com.devsu.banking.domain.service.MovimientoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;
    private final MovimientoMapper movimientoMapper;

    @Value("${banking.daily-withdrawal-limit:1000}")
    private BigDecimal dailyWithdrawalLimit;

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoResponseDto> findAll() {

        return movimientoRepository.findAll().stream()
                .map(movimientoMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MovimientoResponseDto findById(Long id) {

        return movimientoMapper.toDto(getOrThrow(id));
    }

    @Override
    public MovimientoResponseDto registrar(MovimientoRequestDto dto) {

        var cuenta = cuentaRepository.findById(dto.cuentaId())
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta", dto.cuentaId()));

        var valor = normalizeValor(dto.tipoMovimiento(), dto.valor());
        var nuevoSaldo = cuenta.getSaldoInicial().add(valor);

        if ("DEBITO".equals(dto.tipoMovimiento())) {
            if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
                throw new SaldoInsuficienteException();
            }
            checkDailyLimit(cuenta.getId(), valor.abs());
        }

        var movimiento = movimientoMapper.toEntity(dto);
        movimiento.setCuenta(cuenta);
        movimiento.setValor(valor);
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setFecha(LocalDateTime.now());

        cuenta.setSaldoInicial(nuevoSaldo);

        cuentaRepository.save(cuenta);

        return movimientoMapper.toDto(movimientoRepository.save(movimiento));
    }

    @Override
    public MovimientoResponseDto update(Long id, MovimientoRequestDto dto) {

        var movimiento = getOrThrow(id);
        movimiento.setTipoMovimiento(dto.tipoMovimiento());
        movimiento.setValor(normalizeValor(dto.tipoMovimiento(), dto.valor()));

        return movimientoMapper.toDto(movimientoRepository.save(movimiento));
    }

    @Override
    public void delete(Long id) {

        movimientoRepository.delete(getOrThrow(id));
    }

    private BigDecimal normalizeValor(String tipo, BigDecimal valor) {

        return "DEBITO".equals(tipo) ? valor.abs().negate() : valor.abs();
    }

    private void checkDailyLimit(Long cuentaId, BigDecimal montoAbsoluto) {

        var startOfDay = LocalDate.now().atStartOfDay();

        var endOfDay = startOfDay.plusDays(1);

        var debitosHoy = movimientoRepository
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
