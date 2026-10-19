package com.autobots.automanager.documento.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.documento.domain.Documento;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {
  void deleteByClienteId(Long clienteId);
  List<Documento> findByClienteId(Long clienteId);
}
