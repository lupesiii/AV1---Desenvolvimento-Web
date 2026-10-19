package com.autobots.automanager.common;

/** Rotas públicas compartilhadas sem dependências entre controllers de módulos. */
public final class ApiRoutes {
    public static final String ROOT = "/";
    public static final String CLIENTES = "/clientes";
    public static final String CLIENTE = CLIENTES + "/{id}";
    public static final String ENDERECO = CLIENTE + "/endereco";
    public static final String TELEFONES_CLIENTE = CLIENTES + "/{clienteId}/telefones";
    public static final String TELEFONES = "/telefones";
    public static final String TELEFONE = TELEFONES + "/{telefoneId}";
    public static final String DOCUMENTOS = "/documentos";
    public static final String DOCUMENTO = DOCUMENTOS + "/{id}";
    public static final String DOCUMENTOS_CLIENTE = CLIENTES + "/{clienteId}/documentos";

    private ApiRoutes() {
    }
}
