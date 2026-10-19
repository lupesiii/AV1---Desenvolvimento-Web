package com.autobots.automanager.documento.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostDocumentoDTO(
        @NotBlank(message = "o tipo do documento é obrigatório") @Size(max = 255, message = "o tipo deve ter no máximo 255 caracteres") String tipo,
        @NotBlank(message = "o número do documento é obrigatório") @Size(max = 255, message = "o número deve ter no máximo 255 caracteres") String numero,
        @NotBlank @Size(max = 255, message = "o path deve ter no máximo 255 caracteres") String path) {
}