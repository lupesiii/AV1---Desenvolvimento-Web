package com.autobots.automanager.documento.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.autobots.automanager.documento.domain.Documento;
import com.autobots.automanager.documento.models.dto.PostDocumentoDTO;
import com.autobots.automanager.documento.models.dto.PutDocumentoDTO;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Validated
@RestController
@RequestMapping("/documentos")
public class DocumentoController {
  private final DocumentoService documentoService;

  DocumentoController(DocumentoService documentoService) {
    this.documentoService = documentoService;
  }

  @GetMapping
  public ResponseEntity<List<Documento>> obterDocumentos() {
    return ResponseEntity.ok(this.documentoService.obterDocumentos());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Documento> obterDocumentoPorId(
      @PathVariable @Positive(message = "o id deve ser maior que zero") Long id) {
    return ResponseEntity.ok(this.documentoService.obterDocumentoPorId(id));
  }

  @GetMapping("/cliente/{clienteId}")
  public ResponseEntity<List<Documento>> obterDocumentosPorClienteId(
      @PathVariable @Positive(message = "o id do cliente deve ser maior que zero") Long clienteId) {
    return ResponseEntity.ok(this.documentoService.obterDocumentosPorClienteId(clienteId));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletaDocumentoPorId(
      @PathVariable @Positive(message = "o id deve ser maior que zero") Long id) {
    this.documentoService.deletarDocumentoPorId(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping
  public ResponseEntity<String> cadastrarDocumento(@Valid @RequestBody PostDocumentoDTO documento) {
    Long id = this.documentoService.cadastrarDocumento(documento);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(id)
        .toUri();
    return ResponseEntity.created(location).body("Documento criado com sucesso");
  }

  @PutMapping
  public ResponseEntity<Void> atualizarDocumento(@Valid @RequestBody PutDocumentoDTO documentoDTO) {
    this.documentoService.atualizarDocumento(documentoDTO);
    return ResponseEntity.noContent().build();
  }

}