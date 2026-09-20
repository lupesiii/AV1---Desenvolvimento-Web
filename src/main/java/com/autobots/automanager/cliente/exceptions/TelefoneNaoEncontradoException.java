package com.autobots.automanager.cliente.exceptions;

import lombok.Getter;

@Getter
public class TelefoneNaoEncontradoException extends RuntimeException {
  private String customMessage;

  public TelefoneNaoEncontradoException(String titulo, String customMessage) {
    super(titulo);
    this.customMessage = customMessage;
  }
}
