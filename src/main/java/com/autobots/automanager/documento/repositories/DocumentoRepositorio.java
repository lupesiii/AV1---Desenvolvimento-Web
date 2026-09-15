package com.autobots.automanager.documento.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.documento.domain.Documento;

public interface DocumentoRepositorio extends JpaRepository<Documento, Long> {
  List<Documento> findByClienteId(Long clienteId);
}
