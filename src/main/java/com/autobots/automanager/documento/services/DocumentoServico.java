package com.autobots.automanager.documento.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.autobots.automanager.documento.repositories.DocumentoRepositorio;
import com.autobots.automanager.cliente.ClienteFacade;
import com.autobots.automanager.documento.domain.Documento;
import com.autobots.automanager.documento.exceptions.ClienteInvalidoParaDocumentoException;
import com.autobots.automanager.documento.exceptions.DocumentoNaoEncontradoException;

@Service
public class DocumentoServico {
  private final DocumentoRepositorio documentoRepositorio;
  private final ClienteFacade clienteFacade;

  DocumentoServico(DocumentoRepositorio documentoRepositorio, ClienteFacade clienteFacade) {
    this.documentoRepositorio = documentoRepositorio;
    this.clienteFacade = clienteFacade;
  }

  public List<Documento> ObterDocumentos() {
    return this.documentoRepositorio.findAll();
  }

  public List<Documento> obterDocumentosPorClienteId(Long clienteId) {
    if (!clienteFacade.ExisteCliente(clienteId))
      throw new ClienteInvalidoParaDocumentoException(clienteId);

    return this.documentoRepositorio.findByClienteId(clienteId);
  }

  public Documento ObterDocumentoPorId(Long id) {
    Optional<Documento> documentoOpt = this.documentoRepositorio.findById(id);
    Documento documento = documentoOpt
        .orElseThrow(() -> new DocumentoNaoEncontradoException(id, "Não foi possível retornar o documento"));

    return documento;
  }

  // public void CadastrarDocumento(DocumentoCadastro documento) {
  // Cliente cliente = this.clienteGateway.findById(documento.get_idCliente());
  // return cliente;
  // }
}
