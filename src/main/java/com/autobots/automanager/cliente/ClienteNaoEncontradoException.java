package com.autobots.automanager.cliente;

public class ClienteNaoEncontradoException extends RuntimeException {
  private String customMessage;

  public ClienteNaoEncontradoException(Long id, String customMessage) {
    super("Cliente Não Encontrado: " + id);
    this.customMessage = customMessage;
  }
}