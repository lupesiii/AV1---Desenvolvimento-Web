package com.autobots.automanager.documento.exceptions;

import lombok.Getter;

@Getter
public class ClienteInvalidoParaDocumentoException extends RuntimeException {
  private String mensagem;

  public ClienteInvalidoParaDocumentoException(Long clienteId) {
    super("Não é possível encontrar o cliente " + clienteId);
    this.mensagem = "Não é possivel anexar documento";
  }
}
