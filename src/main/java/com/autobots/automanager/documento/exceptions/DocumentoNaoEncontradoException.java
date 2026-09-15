package com.autobots.automanager.documento.exceptions;

public class DocumentoNaoEncontradoException extends RuntimeException {
  private String message;

  public DocumentoNaoEncontradoException(Long id, String message) {
    super("Documento Não Encontrado: " + id);
    this.message = message;
  }
}
