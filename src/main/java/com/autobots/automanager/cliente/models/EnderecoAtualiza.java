package com.autobots.automanager.cliente.models;

import com.autobots.automanager.cliente.domain.Endereco;

import lombok.Getter;

@Getter
public class EnderecoAtualiza {
  private Long clienteId;
  private Endereco endereco;
}