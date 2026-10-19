package com.autobots.automanager.documento.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.autobots.automanager.documento.domain.Documento;
import com.autobots.automanager.documento.models.dto.PostDocumentoDTO;
import com.autobots.automanager.documento.models.dto.PatchDocumentoDTO;
import com.autobots.automanager.documento.services.DocumentoService;

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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Validated
@RestController
public class DocumentoController {
  private final DocumentoService documentoService;

  DocumentoController(DocumentoService documentoService) {
    this.documentoService = documentoService;
  }

  @GetMapping("/documentos")
  public ResponseEntity<List<Documento>> obterDocumentos() {
    return ResponseEntity.ok(this.documentoService.obterDocumentos());
  }

  @GetMapping("/documentos/{id}")
  public ResponseEntity<Documento> obterDocumentoPorId(
      @PathVariable @Positive(message = "o id deve ser maior que zero") Long id) {
    return ResponseEntity.ok(this.documentoService.obterDocumentoPorId(id));
  }

  @GetMapping("/clientes/{clienteId}/documentos")
  public ResponseEntity<List<Documento>> obterDocumentosPorClienteId(
      @PathVariable @Positive(message = "o id do cliente deve ser maior que zero") Long clienteId) {
    return ResponseEntity.ok(this.documentoService.obterDocumentosPorClienteId(clienteId));
  }

  @PostMapping("/clientes/{clienteId}/documentos")
  public ResponseEntity<Void> cadastrarDocumento(
      @PathVariable @Positive(message = "O id do cliente não deve ser menor que zero") Long clienteId,
      @Valid @RequestBody PostDocumentoDTO documentoDTO) {
    Long documentoId = this.documentoService.cadastrarDocumento(clienteId, documentoDTO);
    URI location = ServletUriComponentsBuilder.fromPath("/documentos/{id}")
        .buildAndExpand(documentoId)
        .toUri();
    return ResponseEntity.created(location).build();
  }

  @PatchMapping("/documentos")
  public ResponseEntity<Void> atualizarDocumento(@Valid @RequestBody PatchDocumentoDTO documentoDTO) {
    this.documentoService.atualizarDocumento(documentoDTO);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/documentos/{id}")
  public ResponseEntity<Void> deletaDocumentoPorId(
      @PathVariable @Positive(message = "o id deve ser maior que zero") Long id) {
    this.documentoService.deletarDocumentoPorId(id);
    return ResponseEntity.noContent().build();
  }

}