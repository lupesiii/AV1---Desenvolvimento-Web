package com.autobots.automanager.documento.models.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record PatchDocumentoDTO(
        @NotNull(message = "o id do documento é obrigatório") @Positive(message = "o id do documento deve ser maior que zero") Long id,
        @Pattern(regexp = ".*\\S.*", message = "o tipo não pode ser vazio quando informado") @Size(max = 255, message = "o tipo deve ter no máximo 255 caracteres") String tipo,
        @Pattern(regexp = ".*\\S.*", message = "o número não pode ser vazio quando informado") @Size(max = 255, message = "o número deve ter no máximo 255 caracteres") String numero,
        @Size(max = 255, message = "o path deve ter no máximo 255 caracteres") String path) {
    public PatchDocumentoDTO {
        tipo = normalizar(tipo);
        numero = normalizar(numero);
        path = normalizar(path);
    }

    private static String normalizar(String valor) {
        return valor == null || valor.isBlank() ? null : valor.trim();
    }
}