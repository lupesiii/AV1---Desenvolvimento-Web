package com.autobots.automanager.common;

import java.util.Collection;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.mediatype.Affordances;
import org.springframework.hateoas.server.core.EmbeddedWrapper;
import org.springframework.hateoas.server.core.EmbeddedWrappers;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public final class Hypermedia {
    private Hypermedia() {
    }

    public static Link link(String route, Object... ids) {
        return Link.of(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(route).buildAndExpand(ids).toUriString());
    }

    public static Link action(Link self, String name, HttpMethod method, Link target, Class<?> input) {
        return Affordances.of(self).afford(method).withName(name).withTarget(target)
                .withInput(new RecordInputMetadata(input)).withInputMediaType(MediaType.APPLICATION_JSON).toLink();
    }

    /** O wrapper explícito mantém o nome da relação e o array mesmo sem itens. */
    public static CollectionModel<EmbeddedWrapper> collection(String rel, Collection<?> items, Link... links) {
        EmbeddedWrapper embedded = new EmbeddedWrappers(false).wrap(items, LinkRelation.of(rel));
        return CollectionModel.of(java.util.List.of(embedded), links);
    }
}
