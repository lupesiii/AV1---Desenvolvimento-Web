package com.autobots.automanager.cliente.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.autobots.automanager.cliente.ClienteDTO;
import com.autobots.automanager.cliente.ClienteFacade;
import com.autobots.automanager.cliente.ClienteNaoEncontradoException;
import com.autobots.automanager.cliente.domain.Cliente;
import com.autobots.automanager.cliente.models.ClienteAtualizador;
import com.autobots.automanager.cliente.models.ClienteExclusao;
import com.autobots.automanager.cliente.models.ClienteMapper;
import com.autobots.automanager.cliente.models.Selecionador;
import com.autobots.automanager.cliente.repositories.ClienteRepositorio;

import jakarta.transaction.Transactional;

@Service
public class ClienteServico implements ClienteFacade {
  private final ClienteRepositorio repositorio;
  private final Selecionador<Cliente> selecionador;

  ClienteServico(ClienteRepositorio repositorio, Selecionador<Cliente> selecionador) {
    this.repositorio = repositorio;
    this.selecionador = selecionador;
  }

  public Cliente ObterClientePorId(Long id) {
    List<Cliente> clientes = this.repositorio.findAll();
    return this.selecionador.selecionar(clientes, id);
  }

  @Override
  @Transactional
  public ClienteDTO ObterClienteDTOPorId(Long id) {
    List<Cliente> clientes = this.repositorio.findAll();
    Cliente cliente = this.selecionador.selecionar(clientes, id);
    return ClienteMapper.toDTO(cliente);
  }

  public List<Cliente> ObterClientes() {
    return this.repositorio.findAll();
  }

  @Override
  @Transactional
  public boolean ExisteCliente(Long id) {
    return this.repositorio.existsById(id);
  }

  @Override
  @Transactional
  public void CadastrarCliente(ClienteDTO cliente) {
    Cliente c = new Cliente();
    c.setNome(cliente.nome());
    c.setNomeSocial(cliente.nomeSocial());
    c.setDataNascimento(cliente.dataNascimento());
    c.setDataCadastro(new Date());
    this.repositorio.save(c);
  }

  public void AtualizarCliente(Cliente clienteAtualizado) {
    Optional<Cliente> clienteOpt = this.repositorio.findClienteById(clienteAtualizado.getId());
    Cliente cliente = clienteOpt.orElseThrow(() -> new ClienteNaoEncontradoException(clienteAtualizado.getId()));

    ClienteAtualizador atualizador = new ClienteAtualizador();
    atualizador.atualizar(cliente, clienteAtualizado);
    repositorio.save(cliente);
  }

  public void ExcluirCliente(ClienteExclusao exclusao) {
    Optional<Cliente> clienteOpt = this.repositorio.findClienteById(exclusao.getId());
    Cliente cliente = clienteOpt.orElseThrow(() -> new ClienteNaoEncontradoException(exclusao.getId()));

    repositorio.delete(cliente);
  }
}
