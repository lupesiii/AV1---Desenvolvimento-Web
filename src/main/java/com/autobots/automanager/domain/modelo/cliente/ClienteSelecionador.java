package com.autobots.automanager.domain.modelo.cliente;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.domain.entidades.Cliente;

@Component
public class ClienteSelecionador {
	public Cliente selecionar(List<Cliente> clientes, long id) {
		Cliente selecionado = null;
		for (Cliente cliente : clientes) {
			if (cliente.getId() == id) {
				selecionado = cliente;
			}
		}
		return selecionado;
	}
}