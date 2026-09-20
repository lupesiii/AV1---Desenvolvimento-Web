package com.autobots.automanager.documento.exceptions;

import lombok.Getter;

@Getter
public class DocumentoNaoEncontradoException extends RuntimeException {
  private String customMessage;

  public DocumentoNaoEncontradoException(Long id, String customMessage) {
    super("Documento Não Encontrado: " + id);
    this.customMessage = customMessage;
  }
}
