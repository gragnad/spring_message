package com.nalstudio.springjms.api.hateoas;

import com.nalstudio.springjms.domain.Taco;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;

@Relation(value = "taco", collectionRelation = "tacos")
public class TacoResource extends RepresentationModel<TacoResource> {
    private static final IngredientResourceAssembler ingredientAssembler = new IngredientResourceAssembler();

    @Getter
    private final String name;

    @Getter
    private final Date createAt;

    public TacoResource(Taco taco) {
        this.name = taco.getName();
        this.createAt = taco.getCreateAt();
    }

}
