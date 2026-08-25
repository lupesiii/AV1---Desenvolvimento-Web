package com.autobots.automanager.modelo;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.EntidadeBase;

@Component
public class Selecionador<T extends EntidadeBase> {
  public T selecionar(List<T> lista, long id) {
    T selecionado = null;
    for (T objeto : lista) {
      if (objeto.getId() == id) {
        selecionado = objeto;
      }
    }
    return selecionado;
  }
}