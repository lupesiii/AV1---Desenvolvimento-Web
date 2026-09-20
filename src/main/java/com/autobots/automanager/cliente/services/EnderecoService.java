package com.autobots.automanager.cliente.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autobots.automanager.cliente.domain.Endereco;
import com.autobots.automanager.cliente.exceptions.EnderecoNaoEncontradoException;
import com.autobots.automanager.cliente.models.EnderecoAtualizador;
import com.autobots.automanager.cliente.models.dto.PostEnderecoDTO;
import com.autobots.automanager.cliente.models.dto.PutEnderecoDTO;
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
  public Long cadastrarEnderecoPorClienteId(PostEnderecoDTO enderecoDTO) {
    Cliente cliente = clienteRepository.findById(enderecoDTO.getClienteId())
        .orElseThrow(() -> new ClienteNaoEncontradoException(enderecoDTO.getClienteId(),
            "Não foi possível cadastrar o endereço"));

    Endereco endereco = new Endereco();
    endereco.setRua(enderecoDTO.getRua());
    endereco.setNumero(enderecoDTO.getNumero());
    endereco.setBairro(enderecoDTO.getBairro());
    endereco.setCidade(enderecoDTO.getCidade());
    endereco.setEstado(enderecoDTO.getEstado());
    endereco.setCodigoPostal(enderecoDTO.getCodigoPostal());
    endereco.setCliente(cliente);

    return enderecoRepository.save(endereco).getId();
  }

  public void removerEnderecoPorClienteId(Long id) {
    Optional<Endereco> enderecoOpt = this.enderecoRepository.findByClienteId(id);
    Endereco endereco = enderecoOpt
        .orElseThrow(() -> new EnderecoNaoEncontradoException("Não foi possível remover o endereço"));
    this.enderecoRepository.delete(endereco);
  }

  public void atualizarEnderecoPorClienteId(PutEnderecoDTO enderecoAtualiza) {
    Optional<Endereco> enderecoOpt = this.enderecoRepository.findByClienteId(enderecoAtualiza.getClienteId());
    Endereco endereco = enderecoOpt
        .orElseThrow(() -> new EnderecoNaoEncontradoException(
            "Não foi possivel editar o endereço"));

    EnderecoAtualizador.atualizar(endereco, enderecoAtualiza);
    this.enderecoRepository.save(endereco);

  }
}
