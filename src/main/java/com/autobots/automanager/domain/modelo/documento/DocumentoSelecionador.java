package com.autobots.automanager.domain.modelo.documento;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.domain.entidades.Documento;

@Component
public class DocumentoSelecionador {
  public Documento selecionar(List<Documento> documentos, Long id) {
    Documento selecionado = null;
    for (Documento documento : documentos) {
      if (documento.getId() == id) {
        selecionado = documento;
      }
    }

    return selecionado;
  }
}
