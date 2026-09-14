package com.autobots.automanager.cliente.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.autobots.automanager.cliente.domain.Endereco;
import com.autobots.automanager.cliente.models.EnderecoAtualizador;
import com.autobots.automanager.cliente.models.dto.EnderecoDTO;
import com.autobots.automanager.cliente.ClienteNaoEncontradoException;
import com.autobots.automanager.cliente.domain.Cliente;
import com.autobots.automanager.cliente.repositories.ClienteRepository;
import com.autobots.automanager.cliente.repositories.EnderecoRepository;

@Service
public class EnderecoServico {
  private final EnderecoRepository repositorio;
  private final ClienteRepository clienteRepositorio;

  public EnderecoServico(EnderecoRepository repositorio, ClienteRepository clienteRepositorio) {
    this.repositorio = repositorio;
    this.clienteRepositorio = clienteRepositorio;
  }

  public Endereco obterEnderecoPorClienteId(Long id) {
    Optional<Endereco> enderecoOpt = this.repositorio.findByClienteId(id);
    Endereco endereco = enderecoOpt
        .orElseThrow(() -> new ClienteNaoEncontradoException(id, "Não foi possível encontrar o endereço"));

    return endereco;
  }

  public void cadastrarEnderecoPorClienteId(EnderecoDTO modelEndereco) {
    Optional<Endereco> enderecoOpt = this.repositorio.findByClienteId(modelEndereco.getIdCliente());
    Endereco endereco = enderecoOpt.orElseThrow(
        () -> new ClienteNaoEncontradoException(modelEndereco.getIdCliente(), "Não foi possível cadastrar o endereço"));
    Cliente clienteRef = clienteRepositorio.getReferenceById(modelEndereco.getIdCliente());

    endereco.setCliente(clienteRef);
    this.repositorio.save(endereco);
  }

  public void removerEnderecoPorClienteId(Long id) {
    Optional<Endereco> enderecoOpt = this.repositorio.findByClienteId(id);
    Endereco endereco = enderecoOpt
        .orElseThrow(() -> new ClienteNaoEncontradoException(id, "Não foi possível remover o endereço"));
    this.repositorio.delete(endereco);
  }

  public void editarEnderecoPorClienteId(EnderecoDTO enderecoAtualiza) {
    Optional<Endereco> enderecoOpt = this.repositorio.findByClienteId(enderecoAtualiza.getIdCliente());
    Endereco endereco = enderecoOpt
        .orElseThrow(() -> new ClienteNaoEncontradoException(enderecoAtualiza.getIdCliente(),
            "Não foi possivel editar o endereço"));

    EnderecoAtualizador enderecoAtualizador = new EnderecoAtualizador();
    enderecoAtualizador.atualizar(endereco, enderecoAtualiza.getEndereco());

    this.repositorio.save(endereco);

  }
}
