package com.autobots.automanager.cliente.exceptions;

import lombok.Getter;

@Getter
public class EnderecoNaoEncontradoException extends RuntimeException {
  private String customMessage;

  public EnderecoNaoEncontradoException(String customMessage) {
    super("Not Found");
    this.customMessage = customMessage;
  }
}
