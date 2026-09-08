package com.autobots.automanager.cliente.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.cliente.ClienteDTO;
import com.autobots.automanager.cliente.ClienteFacade;
import com.autobots.automanager.cliente.domain.Cliente;
import com.autobots.automanager.cliente.models.ClienteAtualizador;
import com.autobots.automanager.cliente.models.ClienteExclusao;
import com.autobots.automanager.cliente.models.ClienteMapper;
import com.autobots.automanager.cliente.models.Selecionador;
import com.autobots.automanager.cliente.repositories.ClienteRepositorio;

import jakarta.transaction.Transactional;

@Service
public class ClienteServico implements ClienteFacade {
  @Autowired
  private ClienteRepositorio repositorio;
  @Autowired
  private Selecionador<Cliente> selecionador;

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
    Cliente cliente = repositorio.getById(clienteAtualizado.getId());
    ClienteAtualizador atualizador = new ClienteAtualizador();
    atualizador.atualizar(cliente, clienteAtualizado);
    repositorio.save(cliente);
  }

  public void ExcluirCliente(ClienteExclusao exclusao) {
    Cliente cliente = repositorio.getById(exclusao.getId());
    repositorio.delete(cliente);
  }
}
