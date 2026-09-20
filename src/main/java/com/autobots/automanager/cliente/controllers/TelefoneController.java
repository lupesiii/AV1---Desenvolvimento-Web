package com.autobots.automanager.cliente.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.autobots.automanager.cliente.domain.Telefone;
import com.autobots.automanager.cliente.models.dto.PutTelefoneDTO;
import com.autobots.automanager.cliente.models.dto.TelefoneDTO;
import com.autobots.automanager.cliente.services.TelefoneService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.net.URI;
import java.util.List;

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
@RequestMapping("/telefones")
public class TelefoneController {
  private final TelefoneService telefoneService;

  TelefoneController(TelefoneService telefoneService) {
    this.telefoneService = telefoneService;
  }

  @GetMapping("/cliente/{clienteId}")
  public ResponseEntity<List<Telefone>> obterTelefonesCliente(
      @PathVariable @Positive(message = "o id do cliente deve ser maior que zero") Long clienteId) {
    return ResponseEntity.ok(this.telefoneService.obterTelefonePorClienteId(clienteId));
  }

  @PostMapping
  public ResponseEntity<String> cadastrarTelefone(@Valid @RequestBody TelefoneDTO telefoneDTO) {
    this.telefoneService.cadastrarTelefonePorClienteId(telefoneDTO);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/cliente/{id}")
        .buildAndExpand(telefoneDTO.getClienteId())
        .toUri();
    return ResponseEntity.created(location).body("Telefone cadastrado com sucesso");
  }

  @PutMapping
  public ResponseEntity<Void> atualizarTelefone(@Valid @RequestBody PutTelefoneDTO telefoneDTO) {
    this.telefoneService.atualizarTelefonePorId(telefoneDTO);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{telefoneId}")
  public ResponseEntity<Void> removerTelefone(
      @PathVariable @Positive(message = "o id do cliente deve ser maior que zero") Long telefoneId) {
    this.telefoneService.removerTelefonePorId(telefoneId);
    return ResponseEntity.noContent().build();
  }

}
