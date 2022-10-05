package com.nalstudio.springjms.api.hateoas;

import com.nalstudio.springjms.api.controller.DesignTacoController;
import com.nalstudio.springjms.domain.Taco;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class TacoResourceAssembler extends RepresentationModelAssemblerSupport<Taco, TacoResource> {

    public TacoResourceAssembler() {
        super(DesignTacoController.class, TacoResource.class);
    }

    @Override
    protected TacoResource instantiateModel(Taco entity) {
        return new TacoResource(entity);
    }

    @Override
    public TacoResource toModel(Taco entity) {
        return createModelWithId(entity.getId(), entity);
    }
}
