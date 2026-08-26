package com.autobots.automanager.application.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.application.interfaces.ClienteGateway;
import com.autobots.automanager.domain.entidades.Cliente;
import com.autobots.automanager.domain.entidades.Documento;
import com.autobots.automanager.domain.modelo.Selecionador;
import com.autobots.automanager.domain.modelo.documento.DocumentoCadastro;
import com.autobots.automanager.infrastructure.repositorios.DocumentoRepositorio;

@Service
public class DocumentoServico {
  // https://stackoverflow.com/questions/40620000/spring-autowired-on-properties-vs-constructor
  private final DocumentoRepositorio repositorio;
  private final Selecionador<Documento> selecionador;
  private final ClienteGateway clienteGateway;

  DocumentoServico(ClienteGateway clienteGateway, Selecionador<Documento> selecionador,
      DocumentoRepositorio repositorio) {
    this.clienteGateway = clienteGateway;
    this.selecionador = selecionador;
    this.repositorio = repositorio;
  }

  public List<Documento> ObterDocumentos() {
    return this.repositorio.findAll();
  }

  public Documento ObterDocumentoPorId(Long id) {
    List<Documento> documentos = this.repositorio.findAll();
    return this.selecionador.selecionar(documentos, id);
  }

  public Cliente CadastrarDocumento(DocumentoCadastro documento) {
    Cliente cliente = this.clienteGateway.findById(documento.get_idCliente());
    return cliente;
  }
}
