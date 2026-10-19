package com.autobots.automanager.documento.services;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.autobots.automanager.cliente.ClienteExcluido;
import com.autobots.automanager.documento.repositories.DocumentoRepository;

@Component
public class DocumentoClienteListener {
    private final DocumentoRepository repository;

    public DocumentoClienteListener(DocumentoRepository repository) {
        this.repository = repository;
    }

    @EventListener
    public void removerDocumentos(ClienteExcluido event) {
        repository.deleteByClienteId(event.clienteId());
    }
}
