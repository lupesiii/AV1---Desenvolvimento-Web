package com.autobots.automanager.cliente.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.cliente.domain.Endereco;
import com.autobots.automanager.cliente.models.EnderecoCadastro;
import com.autobots.automanager.cliente.ClienteNaoEncontradoException;
import com.autobots.automanager.cliente.domain.Cliente;
import com.autobots.automanager.cliente.repositories.ClienteRepositorio;

@Service
public class EnderecoServico {
  @Autowired
  ClienteRepositorio repositorio;

  public Endereco obterEnderecoPorClienteId(Long id) {
    Optional<Cliente> clienteOpt = this.repositorio.findById(id);
    Cliente cliente = clienteOpt.orElseThrow(() -> new ClienteNaoEncontradoException(id));

    return cliente.getEndereco();
  }

  public void cadastrarEnderecoPorClienteId(EnderecoCadastro modelEndereco) {
    Optional<Cliente> clienteOpt = this.repositorio.findClienteById(modelEndereco.getIdCliente());
    Cliente cliente = clienteOpt.orElseThrow(() -> new ClienteNaoEncontradoException(modelEndereco.getIdCliente()));

    cliente.setEndereco(modelEndereco.getEndereco());
    this.repositorio.save(cliente);
  }

  public void removerEnderecoPorClienteId(Long id) {
    Optional<Cliente> clienteOpt = this.repositorio.findClienteById(id);
    Cliente cliente = clienteOpt.orElseThrow(() -> new ClienteNaoEncontradoException(id));

    cliente.setEndereco(null);
    this.repositorio.save(cliente);
  }
}
