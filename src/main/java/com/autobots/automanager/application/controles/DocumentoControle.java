package com.autobots.automanager.application.controles;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.application.servicos.DocumentoServico;
import com.autobots.automanager.domain.entidades.Documento;
import com.autobots.automanager.domain.modelo.documento.DocumentoCadastro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
  @Autowired
  private DocumentoServico servico;

  @GetMapping("/")
  public List<Documento> obterDocumentos() {
    return this.servico.ObterDocumentos();
  }

  @GetMapping("/{id}")
  public Documento obterDocumentoPorId(@PathVariable long id) {
    return this.servico.ObterDocumentoPorId(id);
  }

  @PostMapping("/cadastro")
  public void cadastroDocumento(@RequestBody DocumentoCadastro documento) {
    this.servico.CadastrarDocumento(documento);
  }

}
