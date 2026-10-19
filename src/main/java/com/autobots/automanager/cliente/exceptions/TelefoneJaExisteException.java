package com.autobots.automanager.cliente.exceptions;

import lombok.Getter;

@Getter
public class TelefoneJaExisteException extends RuntimeException {
  private String customMessage;

  public TelefoneJaExisteException(String numeroFormatado, String customMessage) {
    super("O telefone de número %s".formatted(numeroFormatado));
    this.customMessage = customMessage;
  }
}
