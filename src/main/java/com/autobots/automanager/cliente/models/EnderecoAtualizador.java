package com.autobots.automanager.cliente.models;

import com.autobots.automanager.cliente.domain.Endereco;
import com.autobots.automanager.cliente.models.dto.PatchEnderecoDTO;
import com.autobots.automanager.common.StringVerificadorNulo;

public class EnderecoAtualizador {
	public static void atualizar(Endereco endereco, PatchEnderecoDTO atualizacao) {
		if (atualizacao != null) {
			if (!StringVerificadorNulo.verificar(atualizacao.estado())) {
				endereco.setEstado(atualizacao.estado());
			}
			if (!StringVerificadorNulo.verificar(atualizacao.cidade())) {
				endereco.setCidade(atualizacao.cidade());
			}
			if (!StringVerificadorNulo.verificar(atualizacao.bairro())) {
				endereco.setBairro(atualizacao.bairro());
			}
			if (!StringVerificadorNulo.verificar(atualizacao.rua())) {
				endereco.setRua(atualizacao.rua());
			}
			if (!StringVerificadorNulo.verificar(atualizacao.numero())) {
				endereco.setNumero(atualizacao.numero());
			}
			if (!StringVerificadorNulo.verificar(atualizacao.codigoPostal())) {
				endereco.setCodigoPostal(atualizacao.codigoPostal());
			}
		}
	}
}