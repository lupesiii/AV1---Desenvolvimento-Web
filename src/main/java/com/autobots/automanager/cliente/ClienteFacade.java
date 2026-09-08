package com.autobots.automanager.cliente;

public interface ClienteFacade {
    ClienteDTO ObterClienteDTOPorId(Long id);

    boolean ExisteCliente(Long id);

    void CadastrarCliente(ClienteDTO cliente);
}