package com.autobots.automanager.cliente;

public class ClienteNaoEncontradoException extends RuntimeException {
  private String message;

  public ClienteNaoEncontradoException(Long id, String message) {
    super("Cliente Não Encontrado: " + id);
    this.message = message;
  }
}