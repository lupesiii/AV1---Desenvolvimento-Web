package com.autobots.automanager.cliente.controllers;

import static com.autobots.automanager.common.Hypermedia.action;
import static com.autobots.automanager.common.Hypermedia.link;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.core.EmbeddedWrapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.cliente.ClienteDTO;
import com.autobots.automanager.cliente.models.dto.PatchClienteDTO;
import com.autobots.automanager.cliente.models.dto.response.ClienteResponseDTO;
import com.autobots.automanager.cliente.models.dto.response.ClienteHalDTO;
import com.autobots.automanager.cliente.models.ClienteModelAssembler;
import com.autobots.automanager.cliente.services.ClienteService;
import com.autobots.automanager.common.ApiRoutes;
import com.autobots.automanager.common.Hypermedia;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping(ApiRoutes.CLIENTES)
public class ClienteController {
	private final ClienteService clienteService;
	private final ClienteModelAssembler assembler;

	ClienteController(ClienteService clienteService, ClienteModelAssembler assembler) {
		this.clienteService = clienteService;
		this.assembler = assembler;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ClienteResponseDTO>> obterClientes() {
		List<ClienteResponseDTO> clientes = this.clienteService.obterClientes();

		return ResponseEntity.ok(clientes);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClienteResponseDTO> obterCliente(
			@PathVariable @Positive(message = "o id deve ser maior que zero") Long id) {
		ClienteResponseDTO cliente = this.clienteService.obterClientePorId(id);
		return ResponseEntity.ok(cliente);
	}

	@GetMapping(produces = { MediaTypes.HAL_JSON_VALUE, MediaTypes.HAL_FORMS_JSON_VALUE })
	public CollectionModel<EmbeddedWrapper> obterClientesHal() {
		return Hypermedia.collection("clientes", clienteService.obterClientes().stream().map(assembler::toModel).toList(),
				action(link(ApiRoutes.CLIENTES), "cadastrarCliente", HttpMethod.POST,
						link(ApiRoutes.CLIENTES), ClienteDTO.class));
	}

	@GetMapping(value = "/{id}", produces = { MediaTypes.HAL_JSON_VALUE, MediaTypes.HAL_FORMS_JSON_VALUE })
	public EntityModel<ClienteHalDTO> obterClienteHal(
			@PathVariable @Positive(message = "o id deve ser maior que zero") Long id) {
		return assembler.toModel(clienteService.obterClientePorId(id));
	}

	@PostMapping
	public ResponseEntity<Void> cadastrarCliente(@Valid @RequestBody ClienteDTO cliente) {
		Long id = this.clienteService.cadastrarCliente(cliente);
		return ResponseEntity.created(link(ApiRoutes.CLIENTE, id).toUri()).build();
	}

	@PatchMapping
	public ResponseEntity<Void> atualizarCliente(@Valid @RequestBody PatchClienteDTO clienteDTO) {
		this.clienteService.atualizarCliente(clienteDTO);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirCliente(@PathVariable @Positive(message = "o id deve ser maior que zero") Long id) {
		this.clienteService.excluirCliente(id);
		return ResponseEntity.noContent().build();
	}
}
