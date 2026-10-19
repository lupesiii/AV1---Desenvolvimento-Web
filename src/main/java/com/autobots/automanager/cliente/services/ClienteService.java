package com.autobots.automanager.cliente.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.autobots.automanager.cliente.ClienteDTO;
import com.autobots.automanager.cliente.ClienteFacade;
import com.autobots.automanager.cliente.ClienteNaoEncontradoException;
import com.autobots.automanager.cliente.domain.Cliente;
import com.autobots.automanager.cliente.models.ClienteAtualizador;
import com.autobots.automanager.cliente.models.ClienteMapper;
import com.autobots.automanager.cliente.models.MapperToDTO;
import com.autobots.automanager.cliente.models.Selecionador;
import com.autobots.automanager.cliente.models.dto.PatchClienteDTO;
import com.autobots.automanager.cliente.models.dto.response.ClienteResponseDTO;
import com.autobots.automanager.cliente.repositories.ClienteRepository;

import jakarta.transaction.Transactional;

@Service
public class ClienteService implements ClienteFacade {
  private final ClienteRepository clienteRepository;
  private final Selecionador<Cliente> selecionador;
  private final MapperToDTO mapper;

  ClienteService(ClienteRepository clienteRepository, Selecionador<Cliente> selecionador,
      MapperToDTO mapper) {
    this.clienteRepository = clienteRepository;
    this.selecionador = selecionador;
    this.mapper = mapper;
  }

  @Override
  public boolean existeCliente(Long id) {
    return this.clienteRepository.existsById(id);
  }

  public List<ClienteResponseDTO> obterClientes() {
    List<Cliente> clientes = this.clienteRepository.findAll();

    List<ClienteResponseDTO> clientesResponseDTO = clientes.stream()
        .map(c -> mapper.toDTO(c, ClienteResponseDTO.class))
        .collect(Collectors.toList());

    return clientesResponseDTO;
  }

  public ClienteResponseDTO obterClientePorId(Long id) {
    Cliente cliente = this.clienteRepository.findById(id)
        .orElseThrow(() -> new ClienteNaoEncontradoException(id, "Não foi possível obter o cliente"));

    ClienteResponseDTO clienteDTO = mapper.toDTO(cliente, ClienteResponseDTO.class);
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

  public void excluirCliente(Long id) {
    Optional<Cliente> clienteOpt = this.clienteRepository.findClienteById(id);
    Cliente cliente = clienteOpt
        .orElseThrow(() -> new ClienteNaoEncontradoException(id, "Não foi possível excluir o cliente"));

    clienteRepository.delete(cliente);
  }
}
