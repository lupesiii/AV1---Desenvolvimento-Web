package com.autobots.automanager.cliente.models;

import static com.autobots.automanager.common.Hypermedia.action;
import static com.autobots.automanager.common.Hypermedia.link;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.autobots.automanager.cliente.models.dto.PatchClienteDTO;
import com.autobots.automanager.cliente.models.dto.PostEnderecoDTO;
import com.autobots.automanager.cliente.models.dto.response.ClienteHalDTO;
import com.autobots.automanager.cliente.models.dto.response.ClienteResponseDTO;
import com.autobots.automanager.common.ApiRoutes;

@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<ClienteResponseDTO, EntityModel<ClienteHalDTO>> {
    @Override
    public EntityModel<ClienteHalDTO> toModel(ClienteResponseDTO cliente) {
        Link self = link(ApiRoutes.CLIENTE, cliente.id());
        self = action(self, "atualizarCliente", HttpMethod.PATCH, link(ApiRoutes.CLIENTES), PatchClienteDTO.class);
        self = action(self, "excluirCliente", HttpMethod.DELETE, self, Void.class);
        if (cliente.endereco() == null) {
            self = action(self, "cadastrarEndereco", HttpMethod.POST,
                    link(ApiRoutes.ENDERECO, cliente.id()), PostEnderecoDTO.class);
        }
        EntityModel<ClienteHalDTO> model = EntityModel.of(
                new ClienteHalDTO(cliente.id(), cliente.nome(), cliente.nomeSocial(), cliente.dataNascimento()),
                self, link(ApiRoutes.CLIENTES).withRel("clientes"),
                link(ApiRoutes.TELEFONES_CLIENTE, cliente.id()).withRel("telefones"),
                link(ApiRoutes.DOCUMENTOS_CLIENTE, cliente.id()).withRel("documentos"));
        if (cliente.endereco() != null) {
            model.add(link(ApiRoutes.ENDERECO, cliente.id()).withRel("endereco"));
        }
        return model;
    }
}
