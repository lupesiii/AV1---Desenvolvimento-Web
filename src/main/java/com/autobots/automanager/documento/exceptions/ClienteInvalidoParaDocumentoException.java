package com.autobots.automanager.documento.exceptions;

import lombok.Getter;

@Getter
public class ClienteInvalidoParaDocumentoException extends RuntimeException {
  private String customMessage;

  public ClienteInvalidoParaDocumentoException(Long clienteId) {
    super("Não é possível encontrar o cliente " + clienteId);
    this.customMessage = "Não é possivel anexar documento";
  }
}
