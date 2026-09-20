package com.autobots.automanager.cliente;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public record ClienteDTO(
        @NotBlank(message = "o nome é obrigatório") @Size(max = 255, message = "o nome deve ter no máximo 255 caracteres") String nome,
        @Size(max = 255, message = "o nome social deve ter no máximo 255 caracteres") String nomeSocial,
        @Past(message = "a data de nascimento deve estar no passado") Date dataNascimento) {
}