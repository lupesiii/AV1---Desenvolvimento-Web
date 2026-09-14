package com.autobots.automanager.cliente.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.cliente.domain.Endereco;
import com.autobots.automanager.cliente.models.dto.EnderecoDTO;
import com.autobots.automanager.cliente.services.EnderecoServico;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/endereco")
public class EnderecoControlller {
  private final EnderecoServico servico;

  public EnderecoControlller(EnderecoServico servico) {
    this.servico = servico;
  }

  @GetMapping("/cliente/{id}")
  public Endereco obterClienteEndereco(@PathVariable Long id) {
    return this.servico.obterEnderecoPorClienteId(id);
  }

  @PostMapping("/cadastrar")
  public void cadastraClienteEndereco(@RequestBody EnderecoDTO modelEndereco) {
    this.servico.cadastrarEnderecoPorClienteId(modelEndereco);

  }

  @DeleteMapping("/remover/{id}")
  public void removerEnderecoCliente(@PathVariable Long id) {
    this.servico.removerEnderecoPorClienteId(id);
  }

  @PutMapping("/editar")
  public void editarEnderecoCliente(@RequestBody EnderecoDTO enderecoAtualiza) {
    this.servico.editarEnderecoPorClienteId(enderecoAtualiza);
  }
}
