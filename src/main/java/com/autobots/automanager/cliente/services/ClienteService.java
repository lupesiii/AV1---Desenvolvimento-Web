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
import com.autobots.automanager.cliente.models.ClienteMapper;
import com.autobots.automanager.cliente.models.Selecionador;
import com.autobots.automanager.cliente.models.dto.PutClienteDTO;
import com.autobots.automanager.cliente.repositories.ClienteRepository;

import jakarta.transaction.Transactional;

@Service
public class ClienteService implements ClienteFacade {
  private final ClienteRepository clienteRepository;
  private final Selecionador<Cliente> selecionador;

  ClienteService(ClienteRepository clienteRepository, Selecionador<Cliente> selecionador) {
    this.clienteRepository = clienteRepository;
    this.selecionador = selecionador;
  }

  @Override
  public boolean existeCliente(Long id) {
    return this.clienteRepository.existsById(id);
  }

  public List<Cliente> obterClientes() {
    return this.clienteRepository.findAll();
  }

  public Cliente obterClientePorId(Long id) {
    Cliente cliente = this.clienteRepository.findById(id)
        .orElseThrow(() -> new ClienteNaoEncontradoException(id, "Não foi possível obter o cliente"));
    return cliente;
  }

  @Override
  public ClienteDTO obterClienteDTOPorId(Long id) {
    Cliente cliente = this.clienteRepository.findById(id)
        .orElseThrow(() -> new ClienteNaoEncontradoException(id, "Não foi possível obter o cliente"));
    return ClienteMapper.toDTO(cliente);
  }

  @Transactional
  public Long cadastrarCliente(ClienteDTO cliente) {
    Cliente c = new Cliente();
    c.setNome(cliente.nome());
    c.setNomeSocial(cliente.nomeSocial());
    c.setDataNascimento(cliente.dataNascimento());
    c.setDataCadastro(new Date());

    Cliente clienteCriado = this.clienteRepository.save(c);
    return clienteCriado.getId();
  }

  public void atualizarCliente(PutClienteDTO clienteDTO) {
    Optional<Cliente> clienteOpt = this.clienteRepository.findClienteById(clienteDTO.getId());
    Cliente cliente = clienteOpt.orElseThrow(
        () -> new ClienteNaoEncontradoException(clienteDTO.getId(), "Não foi possível atualizar o cliente"));

    ClienteAtualizador.atualizar(cliente, clienteDTO);
    clienteRepository.save(cliente);
  }

  public void excluirCliente(Long id) {
    Optional<Cliente> clienteOpt = this.clienteRepository.findClienteById(id);
    Cliente cliente = clienteOpt
        .orElseThrow(() -> new ClienteNaoEncontradoException(id, "Não foi possível excluir o cliente"));

    clienteRepository.delete(cliente);
  }
}
