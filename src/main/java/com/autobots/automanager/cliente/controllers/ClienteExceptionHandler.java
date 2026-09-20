package com.autobots.automanager.cliente.controllers;

import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.autobots.automanager.cliente.ClienteNaoEncontradoException;
import com.autobots.automanager.cliente.exceptions.EnderecoNaoEncontradoException;
import com.autobots.automanager.cliente.exceptions.TelefoneJaExisteException;
import com.autobots.automanager.cliente.exceptions.TelefoneNaoEncontradoException;
import com.autobots.automanager.cliente.models.dto.RespostaErroDTO;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice(basePackageClasses = { ClienteController.class, EnderecoController.class,
    TelefoneController.class })
public class ClienteExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<RespostaErroDTO> handleCorpoInvalido(MethodArgumentNotValidException ex) {
    String mensagem = ex.getBindingResult().getFieldErrors().stream()
        .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
        .sorted()
        .collect(Collectors.joining("; "));
    return ResponseEntity.badRequest().body(new RespostaErroDTO("Dados Inválidos", mensagem));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<RespostaErroDTO> handleParametroInvalido(ConstraintViolationException ex) {
    String mensagem = ex.getConstraintViolations().stream()
        .map(violacao -> {
          String caminho = violacao.getPropertyPath().toString();
          String parametro = caminho.substring(caminho.lastIndexOf('.') + 1);
          return parametro + ": " + violacao.getMessage();
        })
        .sorted()
        .collect(Collectors.joining("; "));
    return ResponseEntity.badRequest().body(new RespostaErroDTO("Dados Inválidos", mensagem));
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<RespostaErroDTO> handleDataViolation(DataIntegrityViolationException ex) {
    RespostaErroDTO erroResposta = new RespostaErroDTO(ex.getMessage(), ex.getLocalizedMessage());
    return new ResponseEntity<RespostaErroDTO>(erroResposta, HttpStatus.CONFLICT);
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
    RespostaErroDTO erroResposta = new RespostaErroDTO(ex.getMessage(), ex.getCustomMessage());
    return new ResponseEntity<RespostaErroDTO>(erroResposta, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(TelefoneNaoEncontradoException.class)
  public ResponseEntity<RespostaErroDTO> handleNoFoundNumber(TelefoneNaoEncontradoException ex) {
    RespostaErroDTO erroResposta = new RespostaErroDTO(ex.getMessage(), ex.getCustomMessage());
    return new ResponseEntity<RespostaErroDTO>(erroResposta, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(EnderecoNaoEncontradoException.class)
  public ResponseEntity<RespostaErroDTO> handleEnderecoNotFound(EnderecoNaoEncontradoException ex) {
    RespostaErroDTO erroResposta = new RespostaErroDTO(ex.getMessage(), ex.getCustomMessage());
    return new ResponseEntity<RespostaErroDTO>(erroResposta, HttpStatus.NOT_FOUND);
  }
}
