package com.autobots.automanager.cliente.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.cliente.domain.Telefone;

public interface TelefoneRepositorio extends JpaRepository<Telefone, Long> {
  List<Telefone> findTelefonesByClienteId(Long id);

  boolean existsByDddAndNumero(String ddd, String numbero);

  boolean existsById(Long id);
}
