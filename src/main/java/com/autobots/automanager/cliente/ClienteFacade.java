package com.autobots.automanager.cliente;

public interface ClienteFacade {
    ClienteDTO obterClienteDTOPorId(Long id);

    boolean existeCliente(Long id);

}