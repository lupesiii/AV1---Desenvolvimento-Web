package com.autobots.automanager.cliente.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RespostaErroDTO {
  private String titulo;
  private String mensagem;

  public RespostaErroDTO(String titulo, String mensagem) {
    this.titulo = titulo;
    this.mensagem = mensagem;
  }
}
