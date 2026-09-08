package com.autobots.automanager.documento.models;

import com.autobots.automanager.documento.domain.Documento;

public class DocumentoCadastro {
  private Documento documento;
  private Long idCliente;

  public Long get_idCliente() {
    return this.idCliente;
  }

  public Documento get_documento() {
    return this.documento;
  }
}
