package com.autobots.automanager.cliente.models;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.cliente.domain.Cliente;

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