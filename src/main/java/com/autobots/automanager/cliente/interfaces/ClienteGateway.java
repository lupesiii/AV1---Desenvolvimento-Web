package com.autobots.automanager.cliente.interfaces;

import com.autobots.automanager.cliente.domain.Cliente;

public interface ClienteGateway {
  Cliente findById(Long id);
}
