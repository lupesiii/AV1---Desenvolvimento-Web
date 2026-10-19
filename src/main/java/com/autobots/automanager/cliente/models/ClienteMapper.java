package com.autobots.automanager.cliente.models;

import com.autobots.automanager.cliente.ClienteDTO;
import com.autobots.automanager.cliente.domain.Cliente;
import com.autobots.automanager.cliente.domain.Endereco;
import com.autobots.automanager.cliente.domain.Telefone;
import com.autobots.automanager.cliente.models.dto.response.ClienteResponseDTO;
import com.autobots.automanager.cliente.models.dto.response.EnderecoResponseDTO;
import com.autobots.automanager.cliente.models.dto.response.TelefoneResponseDTO;

public class ClienteMapper {
  public static ClienteResponseDTO toResponseDTO(Cliente cliente) {
    return new ClienteResponseDTO(cliente.getId(), cliente.getNome(), cliente.getNomeSocial(),
        cliente.getDataNascimento(), toResponseDTO(cliente.getEndereco()),
        cliente.getTelefones().stream().map(ClienteMapper::toResponseDTO).toArray(TelefoneResponseDTO[]::new));
  }

  public static EnderecoResponseDTO toResponseDTO(Endereco endereco) {
    return endereco == null ? null : new EnderecoResponseDTO(endereco.getId(), endereco.getEstado(),
        endereco.getCidade(), endereco.getBairro(), endereco.getRua(), endereco.getNumero(), endereco.getCodigoPostal());
  }

  public static TelefoneResponseDTO toResponseDTO(Telefone telefone) {
    return new TelefoneResponseDTO(telefone.getId(), telefone.getDdd(), telefone.getNumero());
  }

  public static ClienteDTO toDTO(Cliente cliente) {
    return new ClienteDTO(
        cliente.getNome(),
        cliente.getNomeSocial(),
        cliente.getDataNascimento());
  }
}
