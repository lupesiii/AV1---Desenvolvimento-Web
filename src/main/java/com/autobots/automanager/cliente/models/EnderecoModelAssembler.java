package com.autobots.automanager.cliente.models;

import static com.autobots.automanager.common.Hypermedia.action;
import static com.autobots.automanager.common.Hypermedia.link;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.autobots.automanager.cliente.domain.Endereco;
import com.autobots.automanager.cliente.models.dto.PatchEnderecoDTO;
import com.autobots.automanager.cliente.models.dto.response.EnderecoResponseDTO;
import com.autobots.automanager.common.ApiRoutes;

@Component
public class EnderecoModelAssembler implements RepresentationModelAssembler<Endereco, EntityModel<EnderecoResponseDTO>> {
    @Override
    public EntityModel<EnderecoResponseDTO> toModel(Endereco endereco) {
        Long clienteId = endereco.getCliente().getId();
        Link self = link(ApiRoutes.ENDERECO, clienteId);
        self = action(self, "atualizarEndereco", HttpMethod.PATCH, self, PatchEnderecoDTO.class);
        self = action(self, "excluirEndereco", HttpMethod.DELETE, self, Void.class);
        return EntityModel.of(ClienteMapper.toResponseDTO(endereco), self,
                link(ApiRoutes.CLIENTE, clienteId).withRel("cliente"));
    }
}
