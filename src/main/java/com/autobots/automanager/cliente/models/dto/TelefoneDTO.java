package com.autobots.automanager.cliente.models.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class TelefoneDTO {
  @NotNull(message = "o id do cliente é obrigatório")
  @Positive(message = "o id do cliente deve ser maior que zero")
  private Long clienteId;

  @NotNull(message = "o DDD é obrigatório")
  @Pattern(regexp = "\\d{2}", message = "o DDD deve ter 2 dígitos")
  private String ddd;

  @NotNull(message = "o número é obrigatório")
  @Pattern(regexp = "\\d{8,9}", message = "o número deve ter 8 ou 9 dígitos, somente números")
  private String numero;
}