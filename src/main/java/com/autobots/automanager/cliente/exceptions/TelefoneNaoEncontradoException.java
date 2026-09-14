package com.autobots.automanager.cliente.exceptions;

import lombok.Getter;

@Getter
public class TelefoneNaoEncontradoException extends RuntimeException {
  private String mensagem;

  public TelefoneNaoEncontradoException(String titulo, String mensagem) {
    super(titulo);
    this.mensagem = mensagem;
  }
}
