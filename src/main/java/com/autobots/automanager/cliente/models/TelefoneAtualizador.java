package com.autobots.automanager.cliente.models;

import java.util.List;
import java.util.Objects;

import com.autobots.automanager.cliente.domain.Telefone;
import com.autobots.automanager.cliente.models.dto.PatchTelefoneDTO;
import com.autobots.automanager.common.StringVerificadorNulo;

public class TelefoneAtualizador {
	public static void atualizar(Telefone telefone, PatchTelefoneDTO atualizacao) {

		if (atualizacao != null) {
			if (!StringVerificadorNulo.verificar(atualizacao.ddd())) {
				telefone.setDdd(atualizacao.ddd());
			}
			if (!StringVerificadorNulo.verificar(atualizacao.numero())) {
				telefone.setNumero(atualizacao.numero());
			}
		}
	}

	public static void atualizar(List<Telefone> telefones, List<PatchTelefoneDTO> atualizacoes) {
		for (PatchTelefoneDTO atualizacao : atualizacoes) {
			for (Telefone telefone : telefones) {
				if (atualizacao.telefoneId() != null) {
					if (Objects.equals(atualizacao.telefoneId(), telefone.getId())) {
						atualizar(telefone, atualizacao);
					}
				}
			}
		}
	}
}
