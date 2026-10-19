package com.autobots.automanager.cliente.models.dto.response;

import java.time.LocalDate;

public record ClienteHalDTO(Long id, String nome, String nomeSocial, LocalDate dataNascimento) {
}
