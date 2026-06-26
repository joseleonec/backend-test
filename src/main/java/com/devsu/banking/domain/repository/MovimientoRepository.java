package com.devsu.banking.domain.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devsu.banking.domain.entity.Movimiento;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    List<Movimiento> findByCuentaIdAndFechaBetween(Long cuentaId, LocalDateTime from, LocalDateTime to);

    List<Movimiento> findByCuentaClienteIdAndFechaBetween(Long clienteId, LocalDateTime from, LocalDateTime to);

    List<Movimiento> findByCuentaClienteClienteidAndFechaBetween(String clienteid, LocalDateTime from, LocalDateTime to);

    @Query("""
            SELECT COALESCE(SUM(m.valor), 0)
            FROM Movimiento m
            WHERE m.cuenta.id = :cuentaId
              AND m.tipoMovimiento = 'DEBITO'
              AND m.fecha >= :startOfDay
              AND m.fecha < :endOfDay
            """)
    BigDecimal sumDebitosDiarios(
            @Param("cuentaId") Long cuentaId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );
}
