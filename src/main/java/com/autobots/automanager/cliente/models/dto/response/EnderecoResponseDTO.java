package com.autobots.automanager.cliente.models.dto.response;

public record EnderecoResponseDTO(
    Long id,
    String estado,
    String cidade,
    String bairro,
    String rua,
    String numero,
    String codigoPostal) {
}
