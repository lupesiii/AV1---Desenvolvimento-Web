package com.autobots.automanager.documento.models.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PutDocumentoDTO {
  @NotNull(message = "o id do documento é obrigatório")
  @Positive(message = "o id do documento deve ser maior que zero")
  private Long id;

  @Pattern(regexp = ".*\\S.*", message = "o tipo não pode ser vazio quando informado")
  @Size(max = 255, message = "o tipo deve ter no máximo 255 caracteres")
  private String tipo;

  @Pattern(regexp = ".*\\S.*", message = "o número não pode ser vazio quando informado")
  @Size(max = 255, message = "o número deve ter no máximo 255 caracteres")
  private String numero;

  @Size(max = 255, message = "o path deve ter no máximo 255 caracteres")
  private String path;

}