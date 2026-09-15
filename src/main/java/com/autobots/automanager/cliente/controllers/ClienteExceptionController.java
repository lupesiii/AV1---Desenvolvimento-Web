package com.autobots.automanager.cliente.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.autobots.automanager.cliente.ClienteNaoEncontradoException;
import com.autobots.automanager.cliente.exceptions.TelefoneJaExisteException;
import com.autobots.automanager.cliente.exceptions.TelefoneNaoEncontradoException;
import com.autobots.automanager.cliente.models.dto.RespostaErroDTO;

@ControllerAdvice
public class ClienteExceptionController {
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<RespostaErroDTO> handleDataViolation(DataIntegrityViolationException ex) {
    RespostaErroDTO erroResposta = new RespostaErroDTO(ex.getMessage(), ex.toString());
    return new ResponseEntity<RespostaErroDTO>(erroResposta, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<RespostaErroDTO> handleNoResourceFound(NoResourceFoundException ex) {
    RespostaErroDTO erroResposta = new RespostaErroDTO(ex.getMessage(), ex.toString());
    return new ResponseEntity<RespostaErroDTO>(erroResposta, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ClienteNaoEncontradoException.class)
  public ResponseEntity<RespostaErroDTO> handleNoFoundClient(ClienteNaoEncontradoException ex) {
    RespostaErroDTO erroResposta = new RespostaErroDTO(ex.getMessage(), ex.getLocalizedMessage());
    ResponseEntity<RespostaErroDTO> resposta = new ResponseEntity<>(erroResposta, HttpStatus.NOT_FOUND);
    return resposta;
  }

  @ExceptionHandler(TelefoneJaExisteException.class)
  public ResponseEntity<RespostaErroDTO> handleAlreadyExistsNumber(TelefoneJaExisteException ex) {
    RespostaErroDTO erroResposta = new RespostaErroDTO(ex.getMessage(), ex.getMensagem());
    return new ResponseEntity<RespostaErroDTO>(erroResposta, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(TelefoneNaoEncontradoException.class)
  public ResponseEntity<RespostaErroDTO> handleNoFoundNumber(TelefoneNaoEncontradoException ex) {
    RespostaErroDTO erroResposta = new RespostaErroDTO(ex.getMessage(), ex.getMensagem());
    return new ResponseEntity<RespostaErroDTO>(erroResposta, HttpStatus.NOT_FOUND);
  }
}
