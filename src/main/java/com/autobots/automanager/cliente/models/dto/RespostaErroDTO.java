package com.autobots.automanager.cliente.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespostaErroDTO {
  private String titulo;
  private String mensagem;
}
