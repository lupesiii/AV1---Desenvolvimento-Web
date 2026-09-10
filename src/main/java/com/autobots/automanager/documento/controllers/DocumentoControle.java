package com.autobots.automanager.documento.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.documento.domain.Documento;
import com.autobots.automanager.documento.services.DocumentoServico;

import java.util.List;

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
  public List<Documento> obterDocumentos() {
    return this.servico.ObterDocumentos();
  }

  @GetMapping("/{id}")
  public Documento obterDocumentoPorId(@PathVariable long id) {
    return this.servico.ObterDocumentoPorId(id);
  }

  @DeleteMapping("/remover/{id}")
  public void deletaDocumentoPorId(@PathVariable Long id) {

  }

  // @PostMapping("/cadastro")
  // public void cadastroDocumento(@RequestBody DocumentoCadastro documento) {
  // this.servico.CadastrarDocumento(documento);
  // }

}
