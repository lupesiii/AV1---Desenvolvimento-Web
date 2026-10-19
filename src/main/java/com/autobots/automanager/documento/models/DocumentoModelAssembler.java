package com.autobots.automanager.documento.models;

import static com.autobots.automanager.common.Hypermedia.action;
import static com.autobots.automanager.common.Hypermedia.link;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.autobots.automanager.common.ApiRoutes;
import com.autobots.automanager.documento.domain.Documento;
import com.autobots.automanager.documento.models.dto.DocumentoResponseDTO;
import com.autobots.automanager.documento.models.dto.PatchDocumentoDTO;

@Component
public class DocumentoModelAssembler implements RepresentationModelAssembler<Documento, EntityModel<DocumentoResponseDTO>> {
    @Override
    public EntityModel<DocumentoResponseDTO> toModel(Documento documento) {
        Link self = link(ApiRoutes.DOCUMENTO, documento.getId());
        self = action(self, "atualizarDocumento", HttpMethod.PATCH, link(ApiRoutes.DOCUMENTOS), PatchDocumentoDTO.class);
        self = action(self, "excluirDocumento", HttpMethod.DELETE, self, Void.class);
        DocumentoResponseDTO dto = new DocumentoResponseDTO(documento.getId(), documento.getTipo(),
                documento.getNumero(), documento.getPath(), documento.getClienteId());
        return EntityModel.of(dto, self,
                link(ApiRoutes.CLIENTE, documento.getClienteId()).withRel("cliente"),
                link(ApiRoutes.DOCUMENTOS_CLIENTE, documento.getClienteId()).withRel("documentos"));
    }
}
