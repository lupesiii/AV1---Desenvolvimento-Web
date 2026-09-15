package com.autobots.automanager.cliente.models;

import com.autobots.automanager.cliente.domain.Endereco;
import com.autobots.automanager.common.StringVerificadorNulo;

public class EnderecoAtualizador {
	public void atualizar(Endereco endereco, Endereco atualizacao) {
		if (atualizacao != null) {
			if (!StringVerificadorNulo.verificar(atualizacao.getEstado())) {
				endereco.setEstado(atualizacao.getEstado());
			}
			if (!StringVerificadorNulo.verificar(atualizacao.getCidade())) {
				endereco.setCidade(atualizacao.getCidade());
			}
			if (!StringVerificadorNulo.verificar(atualizacao.getBairro())) {
				endereco.setBairro(atualizacao.getBairro());
			}
			if (!StringVerificadorNulo.verificar(atualizacao.getRua())) {
				endereco.setRua(atualizacao.getRua());
			}
			if (!StringVerificadorNulo.verificar(atualizacao.getNumero())) {
				endereco.setNumero(atualizacao.getNumero());
			}
			if (!StringVerificadorNulo.verificar(atualizacao.getInformacoesAdicionais())) {
				endereco.setInformacoesAdicionais(atualizacao.getInformacoesAdicionais());
			}
		}
	}
}