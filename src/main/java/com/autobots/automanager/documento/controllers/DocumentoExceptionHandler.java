package com.autobots.automanager.documento.controllers;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.autobots.automanager.documento.models.dto.RespostaErroDTO;

import jakarta.validation.ConstraintViolationException;

import com.autobots.automanager.documento.exceptions.ClienteInvalidoParaDocumentoException;
import com.autobots.automanager.documento.exceptions.DocumentoNaoEncontradoException;

@RestControllerAdvice(basePackageClasses = DocumentoController.class)
public class DocumentoExceptionHandler {
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

  @ExceptionHandler(ClienteInvalidoParaDocumentoException.class)
  public ResponseEntity<RespostaErroDTO> handleClienteInvalidoException(ClienteInvalidoParaDocumentoException ex) {
    RespostaErroDTO erroResposta = new RespostaErroDTO(ex.getMessage(), ex.getMensagem());
    return new ResponseEntity<RespostaErroDTO>(erroResposta, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(DocumentoNaoEncontradoException.class)
  public ResponseEntity<RespostaErroDTO> handleDocumentoNaoEncontrado(DocumentoNaoEncontradoException ex) {
    RespostaErroDTO erroResposta = new RespostaErroDTO(ex.getMessage(), ex.getCustomMessage());
    return new ResponseEntity<RespostaErroDTO>(erroResposta, HttpStatus.NOT_FOUND);
  }
}
