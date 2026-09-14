package com.autobots.automanager.cliente.models.dto;

import com.autobots.automanager.cliente.domain.Endereco;

import lombok.Getter;

@Getter
public class EnderecoDTO {
  private Long idCliente;
  private Endereco endereco;
}
