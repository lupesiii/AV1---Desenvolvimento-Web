package com.autobots.automanager.cliente.models;

import com.autobots.automanager.cliente.domain.Endereco;

import lombok.Data;

@Data
public class EnderecoCadastro {
  private Long idCliente;
  private Endereco endereco;
}
