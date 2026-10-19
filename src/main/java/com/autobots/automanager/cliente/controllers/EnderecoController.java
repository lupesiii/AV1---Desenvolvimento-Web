package com.autobots.automanager.cliente.controllers;

import static com.autobots.automanager.common.Hypermedia.link;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.cliente.models.ClienteMapper;
import com.autobots.automanager.cliente.models.EnderecoModelAssembler;
import com.autobots.automanager.cliente.models.dto.response.EnderecoResponseDTO;
import com.autobots.automanager.common.ApiRoutes;
import com.autobots.automanager.cliente.models.dto.PostEnderecoDTO;
import com.autobots.automanager.cliente.models.dto.PatchEnderecoDTO;
import com.autobots.automanager.cliente.services.EnderecoService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

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
@RequestMapping(ApiRoutes.ENDERECO)
public class EnderecoController {
  private final EnderecoService enderecoService;
  private final EnderecoModelAssembler assembler;

  public EnderecoController(EnderecoService enderecoService, EnderecoModelAssembler assembler) {
    this.enderecoService = enderecoService;
    this.assembler = assembler;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<EnderecoResponseDTO> obterClienteEndereco(
      @PathVariable @Positive(message = "o id do cliente deve ser maior que 0") Long id) {
    return ResponseEntity.ok(ClienteMapper.toResponseDTO(this.enderecoService.obterEnderecoPorClienteId(id)));
  }

  @GetMapping(produces = { MediaTypes.HAL_JSON_VALUE, MediaTypes.HAL_FORMS_JSON_VALUE })
  public EntityModel<EnderecoResponseDTO> obterClienteEnderecoHal(
      @PathVariable @Positive(message = "o id do cliente deve ser maior que zero") Long id) {
    return assembler.toModel(enderecoService.obterEnderecoPorClienteId(id));
  }

  @PostMapping
  public ResponseEntity<Void> cadastraClienteEndereco(
      @PathVariable @Positive(message = "o id do cliente deve ser maior que 0") Long id,
      @Valid @RequestBody PostEnderecoDTO enderecoDTO) {
    this.enderecoService.cadastrarEnderecoPorClienteId(id, enderecoDTO);
    return ResponseEntity.created(link(ApiRoutes.ENDERECO, id).toUri()).build();
  }

  @PatchMapping
  public ResponseEntity<Void> atualizarEnderecoCliente(
      @PathVariable @Positive(message = "o id do cliente deve ser maior que zero") Long id,
      @Valid @RequestBody PatchEnderecoDTO enderecoAtualiza) {
    this.enderecoService.atualizarEnderecoPorClienteId(id, enderecoAtualiza);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping
  public ResponseEntity<Void> removerEnderecoCliente(
      @PathVariable @Positive(message = "o id do cliente deve ser maior que zero") Long id) {
    this.enderecoService.removerEnderecoPorClienteId(id);
    return ResponseEntity.noContent().build();
  }
}
