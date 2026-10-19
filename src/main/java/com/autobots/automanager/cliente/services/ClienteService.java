package com.autobots.automanager.cliente.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.autobots.automanager.cliente.ClienteDTO;
import com.autobots.automanager.cliente.ClienteFacade;
import com.autobots.automanager.cliente.ClienteNaoEncontradoException;
import com.autobots.automanager.cliente.ClienteExcluido;
import com.autobots.automanager.cliente.domain.Cliente;
import com.autobots.automanager.cliente.models.ClienteAtualizador;
import com.autobots.automanager.cliente.models.ClienteMapper;
import com.autobots.automanager.cliente.models.dto.PatchClienteDTO;
import com.autobots.automanager.cliente.models.dto.response.ClienteResponseDTO;
import com.autobots.automanager.cliente.repositories.ClienteRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService implements ClienteFacade {
  private final ClienteRepository clienteRepository;
  private final ApplicationEventPublisher events;

  ClienteService(ClienteRepository clienteRepository, ApplicationEventPublisher events) {
    this.clienteRepository = clienteRepository;
    this.events = events;
  }

  @Override
  public boolean existeCliente(Long id) {
    return this.clienteRepository.existsById(id);
  }

  @Transactional(readOnly = true)
  public List<ClienteResponseDTO> obterClientes() {
    return clienteRepository.findAll().stream().map(ClienteMapper::toResponseDTO).toList();
  }

  @Transactional(readOnly = true)
  public ClienteResponseDTO obterClientePorId(Long id) {
    Cliente cliente = this.clienteRepository.findById(id)
        .orElseThrow(() -> new ClienteNaoEncontradoException(id, "Não foi possível obter o cliente"));

    ClienteResponseDTO clienteDTO = ClienteMapper.toResponseDTO(cliente);
    return clienteDTO;
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
    c.setDataCadastro(LocalDate.now());

    Cliente clienteCriado = this.clienteRepository.save(c);
    return clienteCriado.getId();
  }

  public void atualizarCliente(PatchClienteDTO clienteDTO) {
    Optional<Cliente> clienteOpt = this.clienteRepository.findClienteById(clienteDTO.id());
    Cliente cliente = clienteOpt.orElseThrow(
        () -> new ClienteNaoEncontradoException(clienteDTO.id(), "Não foi possível atualizar o cliente"));

    ClienteAtualizador.atualizar(cliente, clienteDTO);
    clienteRepository.save(cliente);
  }

  @Transactional
  public void excluirCliente(Long id) {
    Optional<Cliente> clienteOpt = this.clienteRepository.findClienteById(id);
    Cliente cliente = clienteOpt
        .orElseThrow(() -> new ClienteNaoEncontradoException(id, "Não foi possível excluir o cliente"));

    events.publishEvent(new ClienteExcluido(id));
    clienteRepository.delete(cliente);
  }
}
