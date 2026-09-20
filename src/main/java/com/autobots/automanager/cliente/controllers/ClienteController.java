package com.autobots.automanager.cliente.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.autobots.automanager.cliente.ClienteDTO;
import com.autobots.automanager.cliente.domain.Cliente;
import com.autobots.automanager.cliente.models.dto.PutClienteDTO;
import com.autobots.automanager.cliente.services.ClienteService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/clientes")
public class ClienteController {
	private final ClienteService clienteService;

	ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@GetMapping
	public ResponseEntity<List<Cliente>> obterClientes() {
		List<Cliente> clientes = this.clienteService.obterClientes();

		return ResponseEntity.ok(clientes);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> obterCliente(
			@PathVariable @Positive(message = "o id deve ser maior que zero") Long id) {
		Cliente cliente = this.clienteService.obterClientePorId(id);
		return ResponseEntity.ok(cliente);
	}

	@PostMapping
	public ResponseEntity<String> cadastrarCliente(@Valid @RequestBody ClienteDTO cliente) {
		Long id = this.clienteService.cadastrarCliente(cliente);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(id)
				.toUri();
		return ResponseEntity.created(location).body("Cliente cadastrado com sucesso");
	}

	@PutMapping
	public ResponseEntity<Void> atualizarCliente(@Valid @RequestBody PutClienteDTO clienteDTO) {
		this.clienteService.atualizarCliente(clienteDTO);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirCliente(@PathVariable @Positive(message = "o id dee ser maior que zero") Long id) {
		this.clienteService.excluirCliente(id);
		return ResponseEntity.noContent().build();
	}
}
