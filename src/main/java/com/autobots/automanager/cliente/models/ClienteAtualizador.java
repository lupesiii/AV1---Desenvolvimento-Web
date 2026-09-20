package com.autobots.automanager.cliente.models;

import com.autobots.automanager.cliente.domain.Cliente;
import com.autobots.automanager.cliente.models.dto.PutClienteDTO;
import com.autobots.automanager.common.StringVerificadorNulo;

public class ClienteAtualizador {
	public static void atualizar(Cliente cliente, PutClienteDTO atualizacao) {
		if (!StringVerificadorNulo.verificar(atualizacao.getNome())) {
			cliente.setNome(atualizacao.getNome());
		}
		if (!StringVerificadorNulo.verificar(atualizacao.getNomeSocial())) {
			cliente.setNomeSocial(atualizacao.getNomeSocial());
		}

		if (!(atualizacao.getDataNascimento() == null)) {
			cliente.setDataNascimento(atualizacao.getDataNascimento());
		}
	}
}
