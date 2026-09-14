package com.autobots.automanager.cliente.exceptions;

import lombok.Getter;

@Getter
public class TelefoneJaExisteException extends RuntimeException {
  private String mensagem;

  public TelefoneJaExisteException(String titulo, String mensagem) {
    super(titulo);
    this.mensagem = mensagem;
  }
}
