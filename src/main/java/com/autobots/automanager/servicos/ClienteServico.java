package com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.modelo.Selecionador;
import com.autobots.automanager.modelo.cliente.ClienteAtualizador;
import com.autobots.automanager.modelo.cliente.ClienteExclusao;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@Service
public class ClienteServico {
  @Autowired
  private ClienteRepositorio repositorio;
  @Autowired
  private Selecionador<Cliente> selecionador;

  public Cliente ObterCliente(long id) {
    List<Cliente> clientes = this.repositorio.findAll();
    return this.selecionador.selecionar(clientes, id);
  }

  public List<Cliente> ObterClientes() {
    return this.repositorio.findAll();
  }

  public void CadastrarCliente(Cliente cliente) {
    this.repositorio.save(cliente);
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
