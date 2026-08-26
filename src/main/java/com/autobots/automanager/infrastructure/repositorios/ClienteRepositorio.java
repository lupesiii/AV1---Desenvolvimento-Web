package com.autobots.automanager.infrastructure.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.domain.entidades.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
}