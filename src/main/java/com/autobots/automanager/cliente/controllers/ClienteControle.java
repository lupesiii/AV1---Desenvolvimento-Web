package com.autobots.automanager.cliente.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.cliente.ClienteDTO;
import com.autobots.automanager.cliente.domain.Cliente;
import com.autobots.automanager.cliente.models.ClienteExclusao;
import com.autobots.automanager.cliente.services.ClienteServico;

@RestController
@RequestMapping("/cliente")
public class ClienteControle {
	@Autowired
	private ClienteServico servico;

	@GetMapping("/")
	public List<Cliente> obterClientes() {
		return this.servico.ObterClientes();
	}

	@GetMapping("/{id}")
	public Cliente obterCliente(@PathVariable long id) {
		return this.servico.ObterClientePorId(id);
	}

	@PostMapping("/cadastro")
	public void cadastrarCliente(@RequestBody ClienteDTO cliente) {
		this.servico.CadastrarCliente(cliente);
	}

	@PutMapping("/atualizar")
	public void atualizarCliente(@RequestBody Cliente atualizacao) {
		this.servico.AtualizarCliente(atualizacao);
	}

	@DeleteMapping("/excluir")
	public void excluirCliente(@RequestBody ClienteExclusao exclusao) {
		this.servico.ExcluirCliente(exclusao);
	}
}
