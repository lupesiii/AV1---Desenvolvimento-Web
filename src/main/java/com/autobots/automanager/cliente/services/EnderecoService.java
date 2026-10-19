package com.autobots.automanager.cliente.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autobots.automanager.cliente.domain.Endereco;
import com.autobots.automanager.cliente.exceptions.EnderecoNaoEncontradoException;
import com.autobots.automanager.cliente.models.EnderecoAtualizador;
import com.autobots.automanager.cliente.models.dto.PostEnderecoDTO;
import com.autobots.automanager.cliente.models.dto.PatchEnderecoDTO;
import com.autobots.automanager.cliente.ClienteNaoEncontradoException;
import com.autobots.automanager.cliente.domain.Cliente;
import com.autobots.automanager.cliente.repositories.ClienteRepository;
import com.autobots.automanager.cliente.repositories.EnderecoRepository;

@Service
public class EnderecoService {
  private final EnderecoRepository enderecoRepository;
  private final ClienteRepository clienteRepository;

  public EnderecoService(EnderecoRepository enderecoRepository, ClienteRepository clienteRepository) {
    this.enderecoRepository = enderecoRepository;
    this.clienteRepository = clienteRepository;
  }

  public Endereco obterEnderecoPorClienteId(Long id) {
    Optional<Endereco> enderecoOpt = this.enderecoRepository.findByClienteId(id);
    Endereco endereco = enderecoOpt
        .orElseThrow(() -> new EnderecoNaoEncontradoException("Não foi possível encontrar o endereço"));

    return endereco;
  }

  @Transactional
  public Long cadastrarEnderecoPorClienteId(Long clienteId, PostEnderecoDTO enderecoDTO) {
    Cliente cliente = clienteRepository.findById(clienteId)
        .orElseThrow(() -> new ClienteNaoEncontradoException(clienteId,
            "Não foi possível cadastrar o endereço"));

    Endereco endereco = new Endereco();
    endereco.setRua(enderecoDTO.rua());
    endereco.setNumero(enderecoDTO.numero());
    endereco.setBairro(enderecoDTO.bairro());
    endereco.setCidade(enderecoDTO.cidade());
    endereco.setEstado(enderecoDTO.estado());
    endereco.setCodigoPostal(enderecoDTO.codigoPostal());
    endereco.setCliente(cliente);

    return enderecoRepository.save(endereco).getId();
  }

  public void removerEnderecoPorClienteId(Long id) {
    Optional<Endereco> enderecoOpt = this.enderecoRepository.findByClienteId(id);
    Endereco endereco = enderecoOpt
        .orElseThrow(() -> new EnderecoNaoEncontradoException("Não foi possível remover o endereço"));
    this.enderecoRepository.delete(endereco);
  }

  public void atualizarEnderecoPorClienteId(Long clienteId, PatchEnderecoDTO enderecoAtualiza) {
    Optional<Endereco> enderecoOpt = this.enderecoRepository.findByClienteId(clienteId);
    Endereco endereco = enderecoOpt
        .orElseThrow(() -> new EnderecoNaoEncontradoException(
            "Não foi possivel editar o endereço"));

    EnderecoAtualizador.atualizar(endereco, enderecoAtualiza);
    this.enderecoRepository.save(endereco);

  }
}
