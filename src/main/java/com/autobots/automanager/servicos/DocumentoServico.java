package com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelo.Selecionador;
import com.autobots.automanager.repositorios.DocumentoRepositorio;

@Service
public class DocumentoServico {
  @Autowired
  private DocumentoRepositorio repositorio;
  @Autowired
  private Selecionador<Documento> selecionador;

  public List<Documento> ObterDocumentos() {
    return this.repositorio.findAll();
  }

  public Documento ObterDocumentoPorId(Long id) {
    List<Documento> documentos = this.repositorio.findAll();
    return this.selecionador.selecionar(documentos, id);
  }

  public void CadastrarDocumento(Documento documento) {
    this.repositorio.save(documento);
  }
}
