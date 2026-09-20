package com.autobots.automanager.cliente.models.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PutEnderecoDTO {
  @NotNull(message = "o id do cliente é obrigatório")
  @Positive(message = "o id do cliente deve ser maior que zero")
  private Long clienteId;

  @Size(max = 255, message = "o estado deve ter no máximo 255 caracteres")
  private String estado;

  @Pattern(regexp = ".*\\S.*", message = "a cidade não pode ser vazia quando informada")
  @Size(max = 255, message = "a cidade deve ter no máximo 255 caracteres")
  private String cidade;

  @Size(max = 255, message = "o bairro deve ter no máximo 255 caracteres")
  private String bairro;

  @Pattern(regexp = ".*\\S.*", message = "a rua não pode ser vazia quando informada")
  @Size(max = 255, message = "a rua deve ter no máximo 255 caracteres")
  private String rua;

  @Pattern(regexp = ".*\\S.*", message = "o número não pode ser vazio quando informado")
  @Size(max = 255, message = "o número deve ter no máximo 255 caracteres")
  private String numero;

  @Pattern(regexp = "\\d{5}-?\\d{3}", message = "o código postal deve estar no formato 00000-000")
  private String codigoPostal;
}