package com.autobots.automanager.cliente.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
import com.autobots.automanager.cliente.services.ClienteServico;

@RestController
@RequestMapping("/cliente")
public class ClienteControle {
	private final ClienteServico servico;

	ClienteControle(ClienteServico servico) {
		this.servico = servico;
	}

	@GetMapping("/teste/{id}")
	public Boolean obterClientesS(@PathVariable long id) {
		Boolean clientes = this.servico.ExisteCliente(id);

		return clientes;
	}

	@GetMapping("/")
	public ResponseEntity<List<Cliente>> obterClientes() {
		List<Cliente> clientes = this.servico.ObterClientes();

		return ResponseEntity.ok(clientes);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> obterCliente(@PathVariable long id) {
		Cliente cliente = this.servico.ObterClientePorId(id);
		return ResponseEntity.ok(cliente);
	}

	@PostMapping("/cadastro")
	public ResponseEntity<Long> cadastrarCliente(@RequestBody ClienteDTO cliente) {
		return ResponseEntity.created(null).body(this.servico.CadastrarCliente(cliente));
	}

	@PutMapping("/atualizar")
	public void atualizarCliente(@RequestBody Cliente atualizacao) {
		this.servico.AtualizarCliente(atualizacao);
	}

	@DeleteMapping("/excluir")
	public void excluirCliente(@RequestBody Long id) {
		this.servico.ExcluirCliente(id);
	}
}
