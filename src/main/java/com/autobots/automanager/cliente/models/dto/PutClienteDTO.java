package com.autobots.automanager.cliente.models.dto;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PutClienteDTO {
  @NotNull(message = "o id do cliente é obrigatório")
  @Positive(message = "o id do cliente deve ser maior que zero")
  private Long id;

  @Pattern(regexp = ".*\\S.*", message = "o nome não pode ser vazio quando informado")
  @Size(max = 255, message = "o nome deve ter no máximo 255 caracteres")
  private String nome;

  @Pattern(regexp = ".*\\S.*", message = "o nome social não pode ser vazio quando informado")
  @Size(max = 255, message = "o nome social deve ter no máximo 255 caracteres")
  private String nomeSocial;

  @Pattern(regexp = ".*\\S.*", message = "a data não pode ser vazio quando informado")
  @Past(message = "a data de nascimento deve estar no passado")
  private Date dataNascimento;
}