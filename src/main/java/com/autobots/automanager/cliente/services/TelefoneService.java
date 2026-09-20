package com.autobots.automanager.cliente.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.autobots.automanager.cliente.ClienteNaoEncontradoException;
import com.autobots.automanager.cliente.domain.Telefone;
import com.autobots.automanager.cliente.exceptions.TelefoneJaExisteException;
import com.autobots.automanager.cliente.exceptions.TelefoneNaoEncontradoException;
import com.autobots.automanager.cliente.models.TelefoneAtualizador;
import com.autobots.automanager.cliente.models.dto.PutTelefoneDTO;
import com.autobots.automanager.cliente.models.dto.TelefoneDTO;
import com.autobots.automanager.cliente.repositories.ClienteRepository;
import com.autobots.automanager.cliente.repositories.TelefoneRepository;

@Service
public class TelefoneService {
  private final TelefoneRepository telefoneRepositorio;
  private final ClienteRepository clienteRepositorio;

  TelefoneService(TelefoneRepository telefoneRepositorio, ClienteRepository clienteRepositorio) {
    this.telefoneRepositorio = telefoneRepositorio;
    this.clienteRepositorio = clienteRepositorio;
  }

  public List<Telefone> obterTelefonePorClienteId(Long id) {
    List<Telefone> telefones = this.telefoneRepositorio.findTelefonesByClienteId(id);

    return telefones;
  }

  public void cadastrarTelefonePorClienteId(TelefoneDTO telefoneDTO) {
    boolean existsCliente = this.clienteRepositorio.existsById(telefoneDTO.getClienteId());
    if (!existsCliente)
      throw new ClienteNaoEncontradoException(telefoneDTO.getClienteId(), "Não foi possível cadastrar o telefone");

    boolean existsTelefone = this.telefoneRepositorio.existsByDddAndNumero(telefoneDTO.getDdd(),
        telefoneDTO.getNumero());
    if (existsTelefone) {
      String numeroFormatado = Telefone.formataNumero(telefoneDTO.getDdd() + telefoneDTO.getNumero());
      String tituloException = "O telefone de número %s".formatted(numeroFormatado);
      throw new TelefoneJaExisteException(tituloException, "Não foi possível cadastrar o número");
    }

    Telefone telefone = new Telefone();
    telefone.setDdd(telefoneDTO.getDdd());
    telefone.setNumero(telefoneDTO.getNumero());
    telefone.setCliente(clienteRepositorio.getReferenceById(telefoneDTO.getClienteId()));

    this.telefoneRepositorio.save(telefone);
  }

  public void removerTelefonePorId(Long telefoneId) {
    if (!this.telefoneRepositorio.existsById(telefoneId)) {
      throw new TelefoneNaoEncontradoException("Telefone não encontrado: " + telefoneId,
          "Não foi possível remover o número");
    }
    this.telefoneRepositorio.deleteById(telefoneId);
  }

  public void atualizarTelefonePorId(PutTelefoneDTO telefoneDTO) {
    Telefone telefone = this.telefoneRepositorio.findById(telefoneDTO.getTelefoneId())
        .orElseThrow(() -> new TelefoneNaoEncontradoException("Telefone não encontrado: " + telefoneDTO.getTelefoneId(),
            "Não foi possível remover o número"));

    boolean existsCliente = this.clienteRepositorio.existsById(telefone.getCliente().getId());
    if (!existsCliente)
      throw new ClienteNaoEncontradoException(telefone.getCliente().getId(), "Não foi possível cadastrar o telefone");

    TelefoneAtualizador.atualizar(telefone, telefoneDTO);
    this.telefoneRepositorio.save(telefone);
  }
}
