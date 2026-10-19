package com.autobots.automanager.documento.models;

import java.util.List;
import java.util.Objects;

import com.autobots.automanager.common.StringVerificadorNulo;
import com.autobots.automanager.documento.domain.Documento;
import com.autobots.automanager.documento.models.dto.PatchDocumentoDTO;

public class DocumentoAtualizador {
	public static void atualizar(Documento documento, PatchDocumentoDTO atualizacao) {
		if (atualizacao == null) {
			return;
		}

		if (!StringVerificadorNulo.verificar(atualizacao.tipo())) {
			documento.setTipo(atualizacao.tipo());
		}

		if (!StringVerificadorNulo.verificar(atualizacao.numero())) {
			documento.setNumero(atualizacao.numero());
		}

		if (!StringVerificadorNulo.verificar(atualizacao.path())) {
			documento.setPath(atualizacao.path());
		}

	}

	public static void atualizar(List<Documento> documentos, List<PatchDocumentoDTO> atualizacoes) {
		for (PatchDocumentoDTO atualizacao : atualizacoes) {
			for (Documento documento : documentos) {
				if (atualizacao.id() != null && Objects.equals(atualizacao.id(), documento.getId())) {
					atualizar(documento, atualizacao);
				}
			}
		}
	}
}
