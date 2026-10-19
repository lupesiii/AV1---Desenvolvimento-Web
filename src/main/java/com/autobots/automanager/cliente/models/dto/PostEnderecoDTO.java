package com.autobots.automanager.cliente.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PostEnderecoDTO(
                @NotBlank @Size(max = 255, message = "o estado deve ter no máximo 255 caracteres") String estado,
                @NotBlank(message = "a cidade é obrigatória") @Size(max = 255, message = "a cidade deve ter no máximo 255 caracteres") String cidade,
                @NotBlank @Size(max = 255, message = "o bairro deve ter no máximo 255 caracteres") String bairro,
                @NotBlank(message = "a rua é obrigatória") @Size(max = 255, message = "a rua deve ter no máximo 255 caracteres") String rua,
                @NotBlank(message = "o número é obrigatório") @Size(max = 255, message = "o número deve ter no máximo 255 caracteres") String numero,
                @NotBlank @Pattern(regexp = "\\d{5}-?\\d{3}", message = "o código postal deve estar no formato 00000-000") String codigoPostal) {
}