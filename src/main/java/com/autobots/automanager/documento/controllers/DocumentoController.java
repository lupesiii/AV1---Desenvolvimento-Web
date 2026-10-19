package com.autobots.automanager.documento.controllers;

import static com.autobots.automanager.common.Hypermedia.action;
import static com.autobots.automanager.common.Hypermedia.link;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.core.EmbeddedWrapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.documento.domain.Documento;
import com.autobots.automanager.documento.models.dto.PostDocumentoDTO;
import com.autobots.automanager.documento.models.dto.PatchDocumentoDTO;
import com.autobots.automanager.documento.services.DocumentoService;
import com.autobots.automanager.documento.models.DocumentoModelAssembler;
import com.autobots.automanager.documento.models.dto.DocumentoResponseDTO;
import com.autobots.automanager.common.ApiRoutes;
import com.autobots.automanager.common.Hypermedia;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

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
  private final DocumentoModelAssembler assembler;

  DocumentoController(DocumentoService documentoService, DocumentoModelAssembler assembler) {
    this.documentoService = documentoService;
    this.assembler = assembler;
  }

  @GetMapping(value = ApiRoutes.DOCUMENTOS, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Documento>> obterDocumentos() {
    return ResponseEntity.ok(this.documentoService.obterDocumentos());
  }

  @GetMapping(value = ApiRoutes.DOCUMENTO, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Documento> obterDocumentoPorId(
      @PathVariable @Positive(message = "o id deve ser maior que zero") Long id) {
    return ResponseEntity.ok(this.documentoService.obterDocumentoPorId(id));
  }

  @GetMapping(value = ApiRoutes.DOCUMENTOS_CLIENTE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Documento>> obterDocumentosPorClienteId(
      @PathVariable @Positive(message = "o id do cliente deve ser maior que zero") Long clienteId) {
    return ResponseEntity.ok(this.documentoService.obterDocumentosPorClienteId(clienteId));
  }

  @GetMapping(value = ApiRoutes.DOCUMENTOS, produces = { MediaTypes.HAL_JSON_VALUE, MediaTypes.HAL_FORMS_JSON_VALUE })
  public CollectionModel<EmbeddedWrapper> obterDocumentosHal() {
    return Hypermedia.collection("documentos", documentoService.obterDocumentos().stream().map(assembler::toModel).toList(),
        link(ApiRoutes.DOCUMENTOS));
  }

  @GetMapping(value = ApiRoutes.DOCUMENTO, produces = { MediaTypes.HAL_JSON_VALUE, MediaTypes.HAL_FORMS_JSON_VALUE })
  public EntityModel<DocumentoResponseDTO> obterDocumentoHal(
      @PathVariable @Positive(message = "o id deve ser maior que zero") Long id) {
    return assembler.toModel(documentoService.obterDocumentoPorId(id));
  }

  @GetMapping(value = ApiRoutes.DOCUMENTOS_CLIENTE, produces = { MediaTypes.HAL_JSON_VALUE, MediaTypes.HAL_FORMS_JSON_VALUE })
  public CollectionModel<EmbeddedWrapper> obterDocumentosClienteHal(
      @PathVariable @Positive(message = "o id do cliente deve ser maior que zero") Long clienteId) {
    return Hypermedia.collection("documentos",
        documentoService.obterDocumentosPorClienteId(clienteId).stream().map(assembler::toModel).toList(),
        action(link(ApiRoutes.DOCUMENTOS_CLIENTE, clienteId), "cadastrarDocumento", HttpMethod.POST,
            link(ApiRoutes.DOCUMENTOS_CLIENTE, clienteId), PostDocumentoDTO.class),
        link(ApiRoutes.CLIENTE, clienteId).withRel("cliente"));
  }

  @PostMapping(ApiRoutes.DOCUMENTOS_CLIENTE)
  public ResponseEntity<Void> cadastrarDocumento(
      @PathVariable @Positive(message = "O id do cliente não deve ser menor que zero") Long clienteId,
      @Valid @RequestBody PostDocumentoDTO documentoDTO) {
    Long documentoId = this.documentoService.cadastrarDocumento(clienteId, documentoDTO);
    return ResponseEntity.created(link(ApiRoutes.DOCUMENTO, documentoId).toUri()).build();
  }

  @PatchMapping(ApiRoutes.DOCUMENTOS)
  public ResponseEntity<Void> atualizarDocumento(@Valid @RequestBody PatchDocumentoDTO documentoDTO) {
    this.documentoService.atualizarDocumento(documentoDTO);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping(ApiRoutes.DOCUMENTO)
  public ResponseEntity<Void> deletaDocumentoPorId(
      @PathVariable @Positive(message = "o id deve ser maior que zero") Long id) {
    this.documentoService.deletarDocumentoPorId(id);
    return ResponseEntity.noContent().build();
  }

}
