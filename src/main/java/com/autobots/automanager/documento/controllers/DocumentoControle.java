package com.autobots.automanager.documento.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.documento.domain.Documento;
import com.autobots.automanager.documento.services.DocumentoServico;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
  private final DocumentoServico servico;

  DocumentoControle(DocumentoServico servico) {
    this.servico = servico;
  }

  @GetMapping("/")
  public ResponseEntity<List<Documento>> obterDocumentos() {
    return ResponseEntity.ok(this.servico.ObterDocumentos());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Documento> obterDocumentoPorId(@PathVariable long id) {
    return ResponseEntity.ok(this.servico.ObterDocumentoPorId(id));
  }

  @GetMapping("/cliente/{clienteId}")
  public List<Documento> obterDocumentosPorClienteId(@PathVariable Long clienteId) {
    return this.servico.obterDocumentosPorClienteId(clienteId);
  }

  @DeleteMapping("/remover/{id}")
  public void deletaDocumentoPorId(@PathVariable Long id) {

  }

  // @PostMapping("/cadastro")
  // public void cadastroDocumento(@RequestBody DocumentoCadastro documento) {
  // this.servico.CadastrarDocumento(documento);
  // }

}
