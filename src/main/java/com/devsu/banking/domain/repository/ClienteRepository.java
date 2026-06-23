package com.devsu.banking.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsu.banking.domain.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByClienteid(String clienteid);

    boolean existsByClienteid(String clienteid);

    boolean existsByIdentificacion(String identificacion);
}
