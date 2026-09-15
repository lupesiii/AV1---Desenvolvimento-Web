package com.autobots.automanager.documento.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.autobots.automanager.documento.models.dto.RespostaErroDTO;
import com.autobots.automanager.documento.exceptions.ClienteInvalidoParaDocumentoException;

@ControllerAdvice
public class DocumentoExceptionController {
  @ExceptionHandler(ClienteInvalidoParaDocumentoException.class)
  public ResponseEntity<RespostaErroDTO> handleClienteInvalidoException(ClienteInvalidoParaDocumentoException ex) {
    RespostaErroDTO erroResposta = new RespostaErroDTO(ex.getMessage(), ex.getMensagem());
    return new ResponseEntity<RespostaErroDTO>(erroResposta, HttpStatus.NOT_FOUND);
  }
}
