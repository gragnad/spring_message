package com.nalstudio.springjms.api.hateoas;

import com.nalstudio.springjms.api.controller.IngredientController;
import com.nalstudio.springjms.domain.Ingredient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class IngredientResourceAssembler extends RepresentationModelAssemblerSupport<Ingredient, IngredientResource> {

    public IngredientResourceAssembler() {
        super(IngredientController.class, IngredientResource.class);
    }

    @Override
    public IngredientResource toModel(Ingredient entity) {
        return createModelWithId(entity.getId(), entity);
    }

    @Override
    protected IngredientResource instantiateModel(Ingredient entity) {
        return new IngredientResource(entity);
    }

    public CollectionModel<IngredientResource> toCollectionModel(Iterable<? extends Ingredient> entities) {
        return super.toCollectionModel(entities);
    }
}
