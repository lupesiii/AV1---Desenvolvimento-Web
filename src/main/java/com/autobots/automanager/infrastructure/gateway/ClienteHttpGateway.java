package com.autobots.automanager.infrastructure.gateway;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.autobots.automanager.application.interfaces.ClienteGateway;
import com.autobots.automanager.domain.entidades.Cliente;

@Component
public class ClienteHttpGateway implements ClienteGateway {
  private final RestClient restClient;

  public ClienteHttpGateway(RestClient restClient) {
    this.restClient = restClient;
  }

  @Override
  public Cliente findById(Long id) {
    return restClient.get().uri("/cliente/{id}", id).retrieve().body(Cliente.class);
  }
}
