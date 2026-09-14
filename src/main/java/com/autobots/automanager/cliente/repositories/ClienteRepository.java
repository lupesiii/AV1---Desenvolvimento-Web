package com.autobots.automanager.cliente.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.cliente.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
  Optional<Cliente> findClienteById(Long id);

  boolean existsById(Long Id);
}