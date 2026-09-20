package com.autobots.automanager.documento.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostDocumentoDTO {
  @NotBlank(message = "o tipo do documento é obrigatório")
  @Size(max = 255, message = "o tipo deve ter no máximo 255 caracteres")
  private String tipo;

  @NotBlank(message = "o número do documento é obrigatório")
  @Size(max = 255, message = "o número deve ter no máximo 255 caracteres")
  private String numero;

  @Size(max = 255, message = "o path deve ter no máximo 255 caracteres")
  private String path;

  @NotNull(message = "o id do cliente é obrigatório")
  @Positive(message = "o id do cliente deve ser maior que zero")
  private Long clienteId;

}