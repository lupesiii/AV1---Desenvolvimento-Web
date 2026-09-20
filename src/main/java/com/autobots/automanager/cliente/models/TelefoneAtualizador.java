package com.autobots.automanager.cliente.models;

import java.util.List;

import com.autobots.automanager.cliente.domain.Telefone;
import com.autobots.automanager.cliente.models.dto.PutTelefoneDTO;
import com.autobots.automanager.common.StringVerificadorNulo;

public class TelefoneAtualizador {
	public static void atualizar(Telefone telefone, PutTelefoneDTO atualizacao) {

		if (atualizacao != null) {
			if (!StringVerificadorNulo.verificar(atualizacao.getDdd())) {
				telefone.setDdd(atualizacao.getDdd());
			}
			if (!StringVerificadorNulo.verificar(atualizacao.getNumero())) {
				telefone.setNumero(atualizacao.getNumero());
			}
		}
	}

	public static void atualizar(List<Telefone> telefones, List<PutTelefoneDTO> atualizacoes) {
		for (PutTelefoneDTO atualizacao : atualizacoes) {
			for (Telefone telefone : telefones) {
				if (atualizacao.getTelefoneId() != null) {
					if (atualizacao.getTelefoneId() == telefone.getId()) {
						atualizar(telefone, atualizacao);
					}
				}
			}
		}
	}
}