package com.autobots.automanager.cliente.models;

import com.autobots.automanager.cliente.ClienteDTO;
import com.autobots.automanager.cliente.domain.Cliente;

public class ClienteMapper {
  public static ClienteDTO toDTO(Cliente cliente) {
    return new ClienteDTO(
        cliente.getNome(),
        cliente.getNomeSocial(),
        cliente.getDataNascimento());
  }
}
