package com.autobots.automanager.cliente.models.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class PutTelefoneDTO {
  @NotNull(message = "o id do telefone é obrigatório")
  @Positive(message = "o id do telefone deve ser maior que zero")
  private Long telefoneId;

  @Pattern(regexp = "\\d{2}", message = "o DDD deve ter 2 dígitos")
  private String ddd;

  @Pattern(regexp = "\\d{8,9}", message = "o número deve ter 8 ou 9 dígitos, somente números")
  private String numero;
}