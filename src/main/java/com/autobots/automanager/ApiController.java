package com.autobots.automanager;

import static com.autobots.automanager.common.Hypermedia.link;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.common.ApiRoutes;

@RestController
public class ApiController {
    @GetMapping(value = ApiRoutes.ROOT, produces = { MediaTypes.HAL_JSON_VALUE, MediaTypes.HAL_FORMS_JSON_VALUE })
    public RepresentationModel<?> index() {
        return new RepresentationModel<>().add(
                link(ApiRoutes.ROOT).withSelfRel(),
                link(ApiRoutes.CLIENTES).withRel("clientes"),
                link(ApiRoutes.DOCUMENTOS).withRel("documentos"));
    }
}
