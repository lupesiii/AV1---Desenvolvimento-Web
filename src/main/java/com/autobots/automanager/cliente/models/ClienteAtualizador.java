package com.autobots.automanager.cliente.models;

import com.autobots.automanager.cliente.domain.Cliente;
import com.autobots.automanager.cliente.models.dto.PatchClienteDTO;
import com.autobots.automanager.common.StringVerificadorNulo;

public class ClienteAtualizador {
	public static void atualizar(Cliente cliente, PatchClienteDTO atualizacao) {
		if (!StringVerificadorNulo.verificar(atualizacao.nome())) {
			cliente.setNome(atualizacao.nome());
		}
		if (!StringVerificadorNulo.verificar(atualizacao.nomeSocial())) {
			cliente.setNomeSocial(atualizacao.nomeSocial());
		}

		if (!(atualizacao.dataNascimento() == null)) {
			cliente.setDataNascimento(atualizacao.dataNascimento());
		}
	}
}
