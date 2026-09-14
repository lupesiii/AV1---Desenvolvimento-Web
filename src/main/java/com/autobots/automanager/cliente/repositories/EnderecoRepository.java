package com.autobots.automanager.cliente.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.cliente.domain.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
  Optional<Endereco> findByClienteId(Long clienteId);
}
