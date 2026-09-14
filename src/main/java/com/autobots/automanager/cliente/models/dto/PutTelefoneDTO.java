package com.autobots.automanager.cliente.models.dto;

import lombok.Getter;

@Getter
public class PutTelefoneDTO {
  private Long telefoneId;
  private String ddd;
  private String numero;
}
