package com.autobots.automanager.cliente.models.dto.response;

import java.time.LocalDate;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String nomeSocial,
        LocalDate dataNascimento,
        EnderecoResponseDTO endereco,
        TelefoneResponseDTO[] telefones) {
}
