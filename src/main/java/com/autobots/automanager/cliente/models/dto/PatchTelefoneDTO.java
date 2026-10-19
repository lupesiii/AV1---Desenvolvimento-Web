package com.autobots.automanager.cliente.models.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record PatchTelefoneDTO(
                @NotNull(message = "o id do telefone é obrigatório") @Positive(message = "o id do telefone deve ser maior que zero") Long telefoneId,
                @Pattern(regexp = "\\d{2}", message = "o DDD deve ter 2 dígitos") String ddd,
                @Pattern(regexp = "\\d{8,9}", message = "o número deve ter 8 ou 9 dígitos, somente números") String numero

) {
        public PatchTelefoneDTO {
                ddd = normalizar(ddd);
                numero = normalizar(numero);
        }

        private static String normalizar(String valor) {
                return valor == null || valor.isBlank() ? null : valor.trim();
        }
}
