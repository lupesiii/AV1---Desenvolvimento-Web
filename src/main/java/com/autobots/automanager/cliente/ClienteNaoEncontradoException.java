package com.autobots.automanager.cliente;

public class ClienteNaoEncontradoException extends RuntimeException {
  public ClienteNaoEncontradoException(Long id) {
    super("Cliente Não Encontrado: " + id);
  }
}