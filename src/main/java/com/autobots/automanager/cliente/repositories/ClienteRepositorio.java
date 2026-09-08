package com.autobots.automanager.cliente.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.cliente.domain.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
}