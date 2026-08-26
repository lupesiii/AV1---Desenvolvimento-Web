package com.autobots.automanager.application.interfaces;

import com.autobots.automanager.domain.entidades.Cliente;

public interface ClienteGateway {
  Cliente findById(Long id);
}
