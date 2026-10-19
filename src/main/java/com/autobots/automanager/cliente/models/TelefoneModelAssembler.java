package com.autobots.automanager.cliente.models;

import static com.autobots.automanager.common.Hypermedia.action;
import static com.autobots.automanager.common.Hypermedia.link;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.autobots.automanager.cliente.domain.Telefone;
import com.autobots.automanager.cliente.models.dto.PatchTelefoneDTO;
import com.autobots.automanager.cliente.models.dto.response.TelefoneResponseDTO;
import com.autobots.automanager.common.ApiRoutes;

@Component
public class TelefoneModelAssembler implements RepresentationModelAssembler<Telefone, EntityModel<TelefoneResponseDTO>> {
    @Override
    public EntityModel<TelefoneResponseDTO> toModel(Telefone telefone) {
        Long clienteId = telefone.getCliente().getId();
        Link self = link(ApiRoutes.TELEFONE, telefone.getId());
        self = action(self, "atualizarTelefone", HttpMethod.PATCH, link(ApiRoutes.TELEFONES), PatchTelefoneDTO.class);
        self = action(self, "excluirTelefone", HttpMethod.DELETE, self, Void.class);
        return EntityModel.of(ClienteMapper.toResponseDTO(telefone), self,
                link(ApiRoutes.CLIENTE, clienteId).withRel("cliente"),
                link(ApiRoutes.TELEFONES_CLIENTE, clienteId).withRel("telefones"));
    }
}
