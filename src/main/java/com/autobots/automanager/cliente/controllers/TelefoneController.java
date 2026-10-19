package com.autobots.automanager.cliente.controllers;

import static com.autobots.automanager.common.Hypermedia.action;
import static com.autobots.automanager.common.Hypermedia.link;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.core.EmbeddedWrapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.cliente.models.ClienteMapper;
import com.autobots.automanager.cliente.models.TelefoneModelAssembler;
import com.autobots.automanager.cliente.models.dto.response.TelefoneResponseDTO;
import com.autobots.automanager.common.ApiRoutes;
import com.autobots.automanager.common.Hypermedia;
import com.autobots.automanager.cliente.models.dto.PatchTelefoneDTO;
import com.autobots.automanager.cliente.models.dto.TelefoneDTO;
import com.autobots.automanager.cliente.services.TelefoneService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;

@Validated
@RestController
public class TelefoneController {
  private final TelefoneService telefoneService;
  private final TelefoneModelAssembler assembler;

  TelefoneController(TelefoneService telefoneService, TelefoneModelAssembler assembler) {
    this.telefoneService = telefoneService;
    this.assembler = assembler;
  }

  @GetMapping(value = ApiRoutes.TELEFONES_CLIENTE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<TelefoneResponseDTO>> obterTelefonesCliente(
      @PathVariable @Positive(message = "o id do cliente deve ser maior que zero") Long clienteId) {
    return ResponseEntity.ok(this.telefoneService.obterTelefonePorClienteId(clienteId).stream()
        .map(ClienteMapper::toResponseDTO).toList());
  }

  @GetMapping(value = ApiRoutes.TELEFONE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TelefoneResponseDTO> obterTelefone(
      @PathVariable @Positive(message = "o id do telefone deve ser maior que zero") Long telefoneId) {
    return ResponseEntity.ok(ClienteMapper.toResponseDTO(this.telefoneService.obterTelefonePorId(telefoneId)));
  }

  @GetMapping(value = ApiRoutes.TELEFONES_CLIENTE, produces = { MediaTypes.HAL_JSON_VALUE, MediaTypes.HAL_FORMS_JSON_VALUE })
  public CollectionModel<EmbeddedWrapper> obterTelefonesClienteHal(
      @PathVariable @Positive(message = "o id do cliente deve ser maior que zero") Long clienteId) {
    return Hypermedia.collection("telefones",
        telefoneService.obterTelefonePorClienteId(clienteId).stream().map(assembler::toModel).toList(),
        action(link(ApiRoutes.TELEFONES_CLIENTE, clienteId), "cadastrarTelefone", HttpMethod.POST,
            link(ApiRoutes.TELEFONES), TelefoneDTO.class),
        link(ApiRoutes.CLIENTE, clienteId).withRel("cliente"));
  }

  @GetMapping(value = ApiRoutes.TELEFONE, produces = { MediaTypes.HAL_JSON_VALUE, MediaTypes.HAL_FORMS_JSON_VALUE })
  public EntityModel<TelefoneResponseDTO> obterTelefoneHal(
      @PathVariable @Positive(message = "o id do telefone deve ser maior que zero") Long telefoneId) {
    return assembler.toModel(telefoneService.obterTelefonePorId(telefoneId));
  }

  @PostMapping(ApiRoutes.TELEFONES)
  public ResponseEntity<Void> cadastrarTelefone(@Valid @RequestBody TelefoneDTO telefoneDTO) {
    Long telefoneId = this.telefoneService.cadastrarTelefonePorClienteId(telefoneDTO);
    return ResponseEntity.created(link(ApiRoutes.TELEFONE, telefoneId).toUri()).build();
  }

  @PatchMapping(ApiRoutes.TELEFONES)
  public ResponseEntity<Void> atualizarTelefone(@Valid @RequestBody PatchTelefoneDTO telefoneDTO) {
    this.telefoneService.atualizarTelefonePorId(telefoneDTO);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping(ApiRoutes.TELEFONE)
  public ResponseEntity<Void> removerTelefone(
      @PathVariable @Positive(message = "o id do telefone deve ser maior que zero") Long telefoneId) {
    this.telefoneService.removerTelefonePorId(telefoneId);
    return ResponseEntity.noContent().build();
  }

}
