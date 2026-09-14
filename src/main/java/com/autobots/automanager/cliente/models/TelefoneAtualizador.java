package com.autobots.automanager.cliente.models;

import java.util.List;

import com.autobots.automanager.cliente.domain.Telefone;
import com.autobots.automanager.common.StringVerificadorNulo;

public class TelefoneAtualizador {
	public static void atualizar(Telefone telefone, Telefone atualizacao) {

		if (atualizacao != null) {
			if (!StringVerificadorNulo.verificar(atualizacao.getDdd())) {
				telefone.setDdd(atualizacao.getDdd());
			}
			if (!StringVerificadorNulo.verificar(atualizacao.getNumero())) {
				telefone.setNumero(atualizacao.getNumero());
			}
		}
	}

	public void atualizar(List<Telefone> telefones, List<Telefone> atualizacoes) {
		for (Telefone atualizacao : atualizacoes) {
			for (Telefone telefone : telefones) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == telefone.getId()) {
						atualizar(telefone, atualizacao);
					}
				}
			}
		}
	}
}