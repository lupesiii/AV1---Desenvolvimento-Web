package com.autobots.automanager.domain.modelo.documento;

import com.autobots.automanager.domain.entidades.Documento;

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
