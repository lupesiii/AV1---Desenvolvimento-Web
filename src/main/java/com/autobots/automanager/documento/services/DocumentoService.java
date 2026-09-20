package com.autobots.automanager.documento.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.autobots.automanager.documento.repositories.DocumentoRepository;
import com.autobots.automanager.cliente.ClienteFacade;
import com.autobots.automanager.documento.domain.Documento;
import com.autobots.automanager.documento.exceptions.ClienteInvalidoParaDocumentoException;
import com.autobots.automanager.documento.exceptions.DocumentoNaoEncontradoException;
import com.autobots.automanager.documento.models.DocumentoAtualizador;
import com.autobots.automanager.documento.models.dto.PostDocumentoDTO;
import com.autobots.automanager.documento.models.dto.PutDocumentoDTO;

@Service
public class DocumentoService {
  private final DocumentoRepository documentoRepository;
  private final ClienteFacade clienteFacade;

  DocumentoService(DocumentoRepository documentoRepository, ClienteFacade clienteFacade) {
    this.documentoRepository = documentoRepository;
    this.clienteFacade = clienteFacade;
  }

  public List<Documento> obterDocumentos() {
    return this.documentoRepository.findAll();
  }

  public List<Documento> obterDocumentosPorClienteId(Long clienteId) {
    if (!clienteFacade.existeCliente(clienteId))
      throw new ClienteInvalidoParaDocumentoException(clienteId);

    return this.documentoRepository.findByClienteId(clienteId);
  }

  public Documento obterDocumentoPorId(Long id) {
    Optional<Documento> documentoOpt = this.documentoRepository.findById(id);
    Documento documento = documentoOpt
        .orElseThrow(() -> new DocumentoNaoEncontradoException(id, "Não foi possível retornar o documento"));

    return documento;
  }

  public Long cadastrarDocumento(PostDocumentoDTO documentoDTO) {
    if (!clienteFacade.existeCliente(documentoDTO.getClienteId()))
      throw new ClienteInvalidoParaDocumentoException(documentoDTO.getClienteId());

    Documento documento = new Documento();
    documento.setTipo(documentoDTO.getTipo());
    documento.setNumero(documentoDTO.getNumero());
    documento.setPath(documentoDTO.getPath());
    documento.setClienteId(documentoDTO.getClienteId());

    Documento documentoCriado = this.documentoRepository.save(documento);
    return documentoCriado.getId();
  }

  public void atualizarDocumento(PutDocumentoDTO documentoDTO) {
    Optional<Documento> documentoOpt = this.documentoRepository.findById(documentoDTO.getId());
    Documento documento = documentoOpt
        .orElseThrow(
            () -> new DocumentoNaoEncontradoException(documentoDTO.getId(), "Não foi possível atualizar o documento"));

    DocumentoAtualizador.atualizar(documento, documentoDTO);
    this.documentoRepository.save(documento);
  }

  public void deletarDocumentoPorId(Long documentoId) {
    if (!this.documentoRepository.existsById(documentoId))
      throw new DocumentoNaoEncontradoException(documentoId, "Não foi possível deletar o documento");

    this.documentoRepository.deleteById(documentoId);
  }
}
