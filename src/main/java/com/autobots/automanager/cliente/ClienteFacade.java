package com.autobots.automanager.cliente;

public interface ClienteFacade {
    ClienteDTO ObterClienteDTOPorId(Long id);

    boolean ExisteCliente(Long id);

    Long CadastrarCliente(ClienteDTO cliente);
}