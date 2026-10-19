package com.autobots.automanager.cliente.models.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PatchEnderecoDTO(
                @Pattern(regexp = ".*\\S.*", message = "o estado não pode ser vazio quando informado") @Size(max = 255, message = "o estado deve ter no máximo 255 caracteres") String estado,
                @Pattern(regexp = ".*\\S.*", message = "a cidade não pode ser vazia quando informada") @Size(max = 255, message = "a cidade deve ter no máximo 255 caracteres") String cidade,
                @Pattern(regexp = ".*\\S.*", message = "o bairro não pode ser vazio quando informado") @Size(max = 255, message = "o bairro deve ter no máximo 255 caracteres") String bairro,
                @Pattern(regexp = ".*\\S.*", message = "a rua não pode ser vazia quando informada") @Size(max = 255, message = "a rua deve ter no máximo 255 caracteres") String rua,
                @Pattern(regexp = ".*\\S.*", message = "o número não pode ser vazio quando informado") @Size(max = 255, message = "o número deve ter no máximo 255 caracteres") String numero,
                @Pattern(regexp = "\\d{5}-?\\d{3}", message = "o código postal deve estar no formato 00000-000") String codigoPostal

) {
        public PatchEnderecoDTO {
                estado = normalizar(estado);
                cidade = normalizar(cidade);
                bairro = normalizar(bairro);
                rua = normalizar(rua);
                numero = normalizar(numero);
                codigoPostal = normalizar(codigoPostal);
        }

        private static String normalizar(String valor) {
                return valor == null || valor.isBlank() ? null : valor.trim();
        }
}
