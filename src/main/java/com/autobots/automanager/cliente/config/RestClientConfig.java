package com.autobots.automanager.cliente.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
  @Bean
  RestClient clientServiceRestClient(RestClient.Builder builder) {
    return builder.baseUrl("http://client-service").build();
  }
}