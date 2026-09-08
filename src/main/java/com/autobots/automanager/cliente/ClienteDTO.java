package com.autobots.automanager.cliente;

import java.util.Date;

public record ClienteDTO(
                String nome,
                String nomeSocial,
                Date dataNascimento) {
}
