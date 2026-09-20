package com.autobots.automanager.documento.models;

import java.util.List;

import com.autobots.automanager.common.StringVerificadorNulo;
import com.autobots.automanager.documento.domain.Documento;
import com.autobots.automanager.documento.models.dto.PutDocumentoDTO;

public class DocumentoAtualizador {
	public static void atualizar(Documento documento, PutDocumentoDTO atualizacao) {
		if (atualizacao == null) {
			return;
		}

		if (!StringVerificadorNulo.verificar(atualizacao.getTipo())) {
			documento.setTipo(atualizacao.getTipo());
		}

		if (!StringVerificadorNulo.verificar(atualizacao.getNumero())) {
			documento.setNumero(atualizacao.getNumero());
		}

		if (!StringVerificadorNulo.verificar(atualizacao.getPath())) {
			documento.setPath(atualizacao.getPath());
		}

	}

	public static void atualizar(List<Documento> documentos, List<PutDocumentoDTO> atualizacoes) {
		for (PutDocumentoDTO atualizacao : atualizacoes) {
			for (Documento documento : documentos) {
				if (atualizacao.getId() == documento.getId()) {
					atualizar(documento, atualizacao);
				}
			}
		}
	}
}
