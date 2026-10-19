package com.autobots.automanager.cliente.models.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record PatchClienteDTO(
                @NotNull(message = "o id do cliente é obrigatório") @Positive(message = "o id do cliente deve ser maior que zero") Long id,
                @Pattern(regexp = ".*\\S.*", message = "o nome não pode ser vazio quando informado") @Size(max = 255, message = "o nome deve ter no máximo 255 caracteres") String nome,
                @Pattern(regexp = ".*\\S.*", message = "o nome social não pode ser vazio quando informado") @Size(max = 255, message = "o nome social deve ter no máximo 255 caracteres") String nomeSocial,
                @Past(message = "a data de nascimento deve estar no passado") LocalDate dataNascimento) {

        public PatchClienteDTO {
                nome = normalizar(nome);
                nomeSocial = normalizar(nomeSocial);
        }

        private static String normalizar(String valor) {
                return valor == null || valor.isBlank() ? null : valor.trim();
        }

}