package com.autobots.automanager.cliente.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.cliente.domain.Telefone;
import com.autobots.automanager.cliente.services.TelefoneServico;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("telefone")
public class TelefoneControle {
  private final TelefoneServico servico;

  TelefoneControle(TelefoneServico servico) {
    this.servico = servico;
  }

  @GetMapping("/{clienteId}")
  public List<Telefone> obterTelefonesCliente(@PathVariable Long clienteId) {
    return this.servico.obterTelefonePorClienteId(clienteId);
  }

  @DeleteMapping("/{telefoneId}")
  public void removerTelefone(@PathVariable Long telefoneId) {
    this.servico.removerTelefonePorId(telefoneId);
  }

}
