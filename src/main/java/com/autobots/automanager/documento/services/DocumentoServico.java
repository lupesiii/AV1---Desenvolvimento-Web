package com.autobots.automanager.documento.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.autobots.automanager.documento.interfaces.ClienteGateway;
import com.autobots.automanager.documento.repositories.DocumentoRepositorio;
import com.autobots.automanager.documento.domain.Documento;
import com.autobots.automanager.documento.models.DocumentoCadastro;

@Service
public class DocumentoServico {
  // https://stackoverflow.com/questions/40620000/spring-autowired-on-properties-vs-constructor
  private final DocumentoRepositorio repositorio;
  private final ClienteGateway clienteGateway;

  DocumentoServico(ClienteGateway clienteGateway, DocumentoRepositorio repositorio) {
    this.clienteGateway = clienteGateway;
    this.repositorio = repositorio;
  }

  public List<Documento> ObterDocumentos() {
    return this.repositorio.findAll();
  }

  public Documento ObterDocumentoPorId(Long id) {
    List<Documento> documentos = this.repositorio.findAll();
    Documento documento = null;

    for (Documento doc : documentos) {
      if (doc.getId() == id)
        documento = doc;
    }

    return documento;
  }

  // public void CadastrarDocumento(DocumentoCadastro documento) {
  // Cliente cliente = this.clienteGateway.findById(documento.get_idCliente());
  // return cliente;
  // }
}
