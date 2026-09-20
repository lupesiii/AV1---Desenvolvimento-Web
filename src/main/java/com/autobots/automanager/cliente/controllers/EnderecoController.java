package com.autobots.automanager.cliente.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.autobots.automanager.cliente.domain.Endereco;
import com.autobots.automanager.cliente.models.dto.PostEnderecoDTO;
import com.autobots.automanager.cliente.models.dto.PutEnderecoDTO;
import com.autobots.automanager.cliente.services.EnderecoService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@Validated
@RestController
@RequestMapping("/enderecos")
public class EnderecoController {
  private final EnderecoService enderecoService;

  public EnderecoController(EnderecoService enderecoService) {
    this.enderecoService = enderecoService;
  }

  @GetMapping("/cliente/{id}")
  public ResponseEntity<Endereco> obterClienteEndereco(
      @PathVariable @Positive(message = "o id do cliente deve ser maior que 0") Long id) {
    return ResponseEntity.ok(this.enderecoService.obterEnderecoPorClienteId(id));
  }

  @PostMapping
  public ResponseEntity<String> cadastraClienteEndereco(@Valid @RequestBody PostEnderecoDTO enderecoDTO) {
    this.enderecoService.cadastrarEnderecoPorClienteId(enderecoDTO);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/cliente/{id}")
        .buildAndExpand(enderecoDTO.getClienteId())
        .toUri();
    return ResponseEntity.created(location).body("Endereço criado com sucesso");
  }

  @DeleteMapping("/cliente/{id}")
  public ResponseEntity<Void> removerEnderecoCliente(
      @PathVariable @Positive(message = "o id do cliente deve ser maior que zero") Long id) {
    this.enderecoService.removerEnderecoPorClienteId(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping
  public ResponseEntity<Void> atualizarEnderecoCliente(@Valid @RequestBody PutEnderecoDTO enderecoAtualiza) {
    this.enderecoService.atualizarEnderecoPorClienteId(enderecoAtualiza);
    return ResponseEntity.noContent().build();
  }
}
