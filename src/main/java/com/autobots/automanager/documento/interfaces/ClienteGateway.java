package com.autobots.automanager.documento.interfaces;

import com.autobots.automanager.cliente.ClienteDTO;

public interface ClienteGateway {
  ClienteDTO findById(Long id);
}
