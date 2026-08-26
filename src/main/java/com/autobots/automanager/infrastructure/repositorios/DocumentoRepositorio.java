package com.autobots.automanager.infrastructure.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.domain.entidades.Documento;

public interface DocumentoRepositorio extends JpaRepository<Documento, Long> {
}